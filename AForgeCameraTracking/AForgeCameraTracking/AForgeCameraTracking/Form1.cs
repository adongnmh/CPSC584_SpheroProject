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
        Bitmap video;

        int red;
        int green;
        int blue;

        int redUpper = 255, greenUpper=255, blueUpper=255;

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
             * **/
            if (trackingMode == 1)
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
            }
            else if(trackingMode == 2)
            {
                DifferenceEdgeDetector filter = new DifferenceEdgeDetector();
                filter.ApplyInPlace(video2);
                pictureBox2.Image = video2;
            }
            pictureBox1.Image = video;

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

        private void buttonCapture_Click(object sender, EventArgs e)
        {
            pictureBox2.Image = (Bitmap)pictureBox1.Image.Clone();
        }


        private void buttonTracking_Click(object sender, EventArgs e)
        {
            trackingMode = 1;

        }

        private void buttonEdgeDetect_Click(object sender, EventArgs e)
        {
            trackingMode = 2;
        }

        private void numericUpDownRed_ValueChanged(object sender, EventArgs e)
        {
            red = (int) numericUpDownRed.Value;
        }

        private void numericUpDownGreen_ValueChanged(object sender, EventArgs e)
        {
            green = (int)numericUpDownGreen.Value;
        }

        private void numericUpDownGreenUpper_ValueChanged(object sender, EventArgs e)
        {
            greenUpper = (int)numericUpDownGreenUpper.Value;
        }

        private void numericUpDownBlueUpper_ValueChanged(object sender, EventArgs e)
        {
            blueUpper = (int)numericUpDownBlueUpper.Value;
        }

        private void numericUpDownBlue_ValueChanged(object sender, EventArgs e)
        {
            blue = (int)numericUpDownBlue.Value;
        }

        private void numericUpDownRedUpper_ValueChanged(object sender, EventArgs e)
        {
            redUpper = (int)numericUpDownRedUpper.Value;
        }
    }
}
