using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using AForge;
using AForge.Video;
using AForge.Video.DirectShow;
using AForge.Imaging.Filters;
using AForge.Imaging;
using System.Drawing.Imaging;
using AForge.Math.Geometry;
using System.Media;
using AForge.Vision.Motion;

namespace AForgeCameraTracking
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private FilterInfoCollection CaptureDevice;
        private VideoCaptureDevice FinalFrame;

        // Flag to see if tracking is on or off.
        private int trackingMode;
        private Bitmap video;
        private int green, red, blue;
        private int redUpper = 255, greenUpper=255, blueUpper=255;

        // create motion detector
        private MotionDetector detector = new MotionDetector(
            new SimpleBackgroundModelingDetector(),
            new MotionAreaHighlighting());

        private String processValue;

        private List<float> motionHistory = new List<float>();

        

        private void Form1_Load(object sender, EventArgs e)
        {
            CaptureDevice = new FilterInfoCollection(FilterCategory.VideoInputDevice);
            /**
             * For Loop getting all the Capture Devices into the drop down Menu
             * */
            foreach (FilterInfo Device in CaptureDevice)
            {
                deviceBox.Items.Add(Device.Name);
            }
            deviceBox.SelectedIndex = 0;
            FinalFrame = new VideoCaptureDevice();
        }

        private void buttonStart_Click(object sender, EventArgs e)
        {
            FinalFrame = new VideoCaptureDevice(CaptureDevice[deviceBox.SelectedIndex].MonikerString);
            FinalFrame.NewFrame += new NewFrameEventHandler(FinalFrame_NewFrame);
            FinalFrame.Start();
           
        }

        /**
         * Filling out the First PictureBox with the WebCam image.
         * Here we are filling the frame with the screen capture from the web cam in pictureBox1. In pictureBox2 we are filling the box
         * with the tracking of the object.
         * Tracking - Currently only done with color filtering. 
         **/
        void FinalFrame_NewFrame(object sender, NewFrameEventArgs eventArgs)
        {
            video = (Bitmap)eventArgs.Frame.Clone();
            Bitmap video2 = (Bitmap)eventArgs.Frame.Clone();
            pictureBox1.Image = video;



            /**
             * If the trackingMode == 1 then it means we are in tracking mode.
             * NOTE: This is old code! We are currently not using this at all. This is just here for reference!
             * **/

            /*if (trackingMode == 1)
            {
                // Filter Range - You can specify the color that you want the webcam to recogonize only here. Adjust with the 
                // sliders here. More can be found on the documentation on AForge.
                ColorFiltering colorFilter = new ColorFiltering();
                colorFilter.Red = new IntRange(red, redUpper);
                colorFilter.Green = new IntRange(green, greenUpper);
                colorFilter.Blue = new IntRange(blue, blueUpper);
                colorFilter.ApplyInPlace(video2);

                // Blob counter counts and extracts stand alone objects in images using connected components labelling algortithm.
                BlobCounter blobCounter = new BlobCounter();
                blobCounter.MinHeight = 130;
                blobCounter.MinWidth = 130;
                blobCounter.ObjectsOrder = ObjectsOrder.Size;
                blobCounter.ProcessImage(video2);

                Rectangle[] rekt = blobCounter.GetObjectsRectangles();
                if(rekt.Length > 0)
                {
                    Rectangle blobObj = rekt[0];

                    Console.WriteLine(blobObj.X);
                    Console.WriteLine(blobObj.Y);

                    Graphics graphic = Graphics.FromImage(video2);
                    using (Pen pen = new Pen(Color.White, 3))
                    {
                        graphic.DrawRectangle(pen, blobObj);

                    }
                    graphic.Dispose();
                }
                pictureBox2.Image = video2;
            }*/

            if(trackingMode == 2)
            {

                EuclideanColorFiltering filter = new EuclideanColorFiltering();
                // set center colol and radius
                filter.CenterColor = new RGB(255, 255, 255);
                filter.Radius = 150;
                filter.ApplyInPlace(video2);

                // create filter
                BlobsFiltering bFilter = new BlobsFiltering();
                // configure filter
                bFilter.CoupledSizeFiltering = true;
                bFilter.MinWidth = 10;
                bFilter.MinHeight = 10;
                // apply the filter
                bFilter.ApplyInPlace(video2);


                BlobCounter blobCounter = new BlobCounter();
                blobCounter.FilterBlobs = true;
                blobCounter.MinHeight = 10;
                blobCounter.MinWidth = 10;
                blobCounter.ObjectsOrder = ObjectsOrder.Size;

                blobCounter.ProcessImage(video2);
                Blob[] blobs = blobCounter.GetObjectsInformation();

                // step 3 - check objects' type and highlight
                SimpleShapeChecker shapeChecker = new SimpleShapeChecker();

                Graphics g = Graphics.FromImage(video2);
                Pen yellowPen = new Pen(Color.Yellow, 5); // circles

                for (int i = 0, n = blobs.Length; i < n; i++)
                {
                    List<IntPoint> edgePoints = blobCounter.GetBlobsEdgePoints(blobs[i]);

                    AForge.Point center;
                    float radius;
                    

                    if (shapeChecker.IsCircle(edgePoints, out center, out radius))
                    {


                        Console.WriteLine("Sphero Size is:");
                        Console.WriteLine("Sphero Width is:" + blobs[i].Rectangle.Width);
                        Console.WriteLine("Sphero Height is:" +blobs[i].Rectangle.Height);


                        //Printing the Sphero Coordinates here:
                        Console.WriteLine("Sphero Coordinates Are:");
                        Console.WriteLine(center.X);
                        Console.WriteLine(center.Y);
                        g.DrawEllipse(yellowPen,
                            (int)(center.X - radius),
                            (int)(center.Y - radius),
                            (int)(radius * 2),
                            (int)(radius * 2));
                    }
                }
                pictureBox2.Image = video2;



            }

            else if(trackingMode == 3)
            {
                detector.ProcessFrame(video2);
                detector.MotionDetectionAlgorithm = new SimpleBackgroundModelingDetector(true, true);
                detector.MotionProcessingAlgorithm = new MotionBorderHighlighting();


                BlobCounter blobCounter = new BlobCounter();
                blobCounter.MinHeight = 5;
                blobCounter.MinWidth = 5;
                blobCounter.ObjectsOrder = ObjectsOrder.Size;

                blobCounter.ProcessImage(video2);
                Blob[] blobs = blobCounter.GetObjectsInformation();

                for (int i = 0, n = blobs.Length; i < n; i++)
                {
                    Console.WriteLine(blobs.Length);
                    Console.WriteLine(blobs[i].Rectangle.Location);
                }


                processValue = detector.ProcessFrame(video2).ToString();
                pictureBox2.Image = video2;
            }

            pictureBox1.Image = video;

        }

        // Set new motion detection algorithm
        private void SetMotionDetectionAlgorithm(IMotionDetector detectionAlgorithm)
        {
            lock (this)
            {
                detector.MotionDetectionAlgorithm = detectionAlgorithm;

                if (detectionAlgorithm is TwoFramesDifferenceDetector)
                {
                    if (
                        (detector.MotionProcessingAlgorithm is MotionBorderHighlighting) ||
                        (detector.MotionProcessingAlgorithm is BlobCountingObjectsProcessing))
                    {
                        SetMotionProcessingAlgorithm(new MotionAreaHighlighting());
                    }
                }
            }
        }

        // Set new motion processing algorithm
        private void SetMotionProcessingAlgorithm(IMotionProcessing processingAlgorithm)
        {
            lock (this)
            {
                detector.MotionProcessingAlgorithm = processingAlgorithm;
            }
        }

        /**
         * Stoping the webcam process
         * */
        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            if(FinalFrame.IsRunning == true)
            {
                FinalFrame.Stop();
            }
        }

        private void btnMotion_Click(object sender, EventArgs e)
        {
            trackingMode = 3;
            textBoxMotionX.Text = processValue;
        }

        private void buttonTracking_Click(object sender, EventArgs e)
        {
            trackingMode = 1;

        }

        private void buttonEdgeDetect_Click(object sender, EventArgs e)
        {
            trackingMode = 2;
        }

    }
}
