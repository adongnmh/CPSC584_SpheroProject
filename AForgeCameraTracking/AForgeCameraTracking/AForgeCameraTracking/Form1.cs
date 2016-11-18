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
using System.Drawing;

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
        private int mode;
        Bitmap video;

        int red;
        int green;
        int blue;

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
         * Filling out the First PictureBox with the WebCam image
         **/
        void FinalFrame_NewFrame(object sender, NewFrameEventArgs eventArgs)
        {
            video = (Bitmap)eventArgs.Frame.Clone();
            Bitmap video2 = (Bitmap)eventArgs.Frame.Clone();
            pictureBox1.Image = video;
            if(mode == 1)
            {
                // Filter Range 
                ColorFiltering colorFilter = new ColorFiltering();
                colorFilter.Red = new IntRange(red, 255);
                colorFilter.Green = new IntRange(green, 75);
                colorFilter.Blue = new IntRange(blue, 75);
                colorFilter.ApplyInPlace(video2);

                // Blob counter counts and extracts stand alone objects in images using connected components labelling algortithm.
                BlobCounter blobCounter = new BlobCounter();
                blobCounter.MinHeight = 20;
                blobCounter.MinWidth = 20;
                blobCounter.ObjectsOrder = ObjectsOrder.Size;
                blobCounter.ProcessImage(video2);

                Rectangle[] rekt = blobCounter.GetObjectsRectangles();
                if(rekt.Length > 0)
                {
                    Rectangle blobObj = rekt[0];
                    Graphics graphic = Graphics.FromImage(video2);
                    using (Pen pen = new Pen(Color.White, 3))
                    {
                        graphic.DrawRectangle(pen, blobObj);

                    }
                    graphic.Dispose();
                }
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
            mode = 1;

        }

        private void trackBarRed_Scroll(object sender, EventArgs e)
        {
            red = (int)trackBarRed.Value;
            numericUpDownRed.Value = red;
        }

        private void trackBarGreen_Scroll(object sender, EventArgs e)
        {
            green = (int)trackBarGreen.Value;
            numericUpDownGreen.Value = green;
        }

        private void trackBarBlue_Scroll(object sender, EventArgs e)
        {
            blue = (int)trackBarBlue.Value;
            numericUpDownBlue.Value = blue;
        }
    }
}
