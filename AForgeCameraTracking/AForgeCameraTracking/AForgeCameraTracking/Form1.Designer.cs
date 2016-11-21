namespace AForgeCameraTracking
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.buttonStart = new System.Windows.Forms.Button();
            this.deviceBox = new System.Windows.Forms.ComboBox();
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.pictureBox2 = new System.Windows.Forms.PictureBox();
            this.buttonCapture = new System.Windows.Forms.Button();
            this.buttonTracking = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.buttonEdgeDetect = new System.Windows.Forms.Button();
            this.numericUpDownRed = new System.Windows.Forms.NumericUpDown();
            this.numericUpDownGreen = new System.Windows.Forms.NumericUpDown();
            this.numericUpDownBlue = new System.Windows.Forms.NumericUpDown();
            this.numericUpDownRedUpper = new System.Windows.Forms.NumericUpDown();
            this.numericUpDownGreenUpper = new System.Windows.Forms.NumericUpDown();
            this.numericUpDownBlueUpper = new System.Windows.Forms.NumericUpDown();
            this.tableLayoutPanel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownRed)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownGreen)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownBlue)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownRedUpper)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownGreenUpper)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownBlueUpper)).BeginInit();
            this.SuspendLayout();
            // 
            // buttonStart
            // 
            this.buttonStart.Location = new System.Drawing.Point(42, 12);
            this.buttonStart.Name = "buttonStart";
            this.buttonStart.Size = new System.Drawing.Size(75, 23);
            this.buttonStart.TabIndex = 0;
            this.buttonStart.Text = "Start";
            this.buttonStart.UseVisualStyleBackColor = true;
            this.buttonStart.Click += new System.EventHandler(this.buttonStart_Click);
            // 
            // deviceBox
            // 
            this.deviceBox.FormattingEnabled = true;
            this.deviceBox.Location = new System.Drawing.Point(132, 14);
            this.deviceBox.Name = "deviceBox";
            this.deviceBox.Size = new System.Drawing.Size(121, 21);
            this.deviceBox.TabIndex = 1;
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 2;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.Controls.Add(this.pictureBox1, 0, 0);
            this.tableLayoutPanel1.Controls.Add(this.pictureBox2, 1, 0);
            this.tableLayoutPanel1.Location = new System.Drawing.Point(42, 42);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 1;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(1056, 461);
            this.tableLayoutPanel1.TabIndex = 2;
            // 
            // pictureBox1
            // 
            this.pictureBox1.BackColor = System.Drawing.SystemColors.AppWorkspace;
            this.pictureBox1.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.pictureBox1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pictureBox1.Location = new System.Drawing.Point(3, 3);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(522, 455);
            this.pictureBox1.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.pictureBox1.TabIndex = 0;
            this.pictureBox1.TabStop = false;
            // 
            // pictureBox2
            // 
            this.pictureBox2.BackColor = System.Drawing.SystemColors.AppWorkspace;
            this.pictureBox2.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.pictureBox2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pictureBox2.Location = new System.Drawing.Point(531, 3);
            this.pictureBox2.Name = "pictureBox2";
            this.pictureBox2.Size = new System.Drawing.Size(522, 455);
            this.pictureBox2.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.pictureBox2.TabIndex = 1;
            this.pictureBox2.TabStop = false;
            // 
            // buttonCapture
            // 
            this.buttonCapture.Location = new System.Drawing.Point(270, 12);
            this.buttonCapture.Name = "buttonCapture";
            this.buttonCapture.Size = new System.Drawing.Size(75, 23);
            this.buttonCapture.TabIndex = 3;
            this.buttonCapture.Text = "Capture";
            this.buttonCapture.UseVisualStyleBackColor = true;
            this.buttonCapture.Click += new System.EventHandler(this.buttonCapture_Click);
            // 
            // buttonTracking
            // 
            this.buttonTracking.Location = new System.Drawing.Point(352, 12);
            this.buttonTracking.Name = "buttonTracking";
            this.buttonTracking.Size = new System.Drawing.Size(75, 23);
            this.buttonTracking.TabIndex = 4;
            this.buttonTracking.Text = "TrackObject";
            this.buttonTracking.UseVisualStyleBackColor = true;
            this.buttonTracking.Click += new System.EventHandler(this.buttonTracking_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(42, 516);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(27, 13);
            this.label1.TabIndex = 8;
            this.label1.Text = "Red";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(42, 577);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(36, 13);
            this.label2.TabIndex = 9;
            this.label2.Text = "Green";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(42, 629);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(28, 13);
            this.label3.TabIndex = 10;
            this.label3.Text = "Blue";
            // 
            // buttonEdgeDetect
            // 
            this.buttonEdgeDetect.Location = new System.Drawing.Point(434, 12);
            this.buttonEdgeDetect.Name = "buttonEdgeDetect";
            this.buttonEdgeDetect.Size = new System.Drawing.Size(102, 23);
            this.buttonEdgeDetect.TabIndex = 14;
            this.buttonEdgeDetect.Text = "EdgeDetection";
            this.buttonEdgeDetect.UseVisualStyleBackColor = true;
            this.buttonEdgeDetect.Click += new System.EventHandler(this.buttonEdgeDetect_Click);
            // 
            // numericUpDownRed
            // 
            this.numericUpDownRed.Increment = new decimal(new int[] {
            5,
            0,
            0,
            0});
            this.numericUpDownRed.Location = new System.Drawing.Point(94, 514);
            this.numericUpDownRed.Maximum = new decimal(new int[] {
            255,
            0,
            0,
            0});
            this.numericUpDownRed.Name = "numericUpDownRed";
            this.numericUpDownRed.Size = new System.Drawing.Size(120, 20);
            this.numericUpDownRed.TabIndex = 15;
            this.numericUpDownRed.ValueChanged += new System.EventHandler(this.numericUpDownRed_ValueChanged);
            // 
            // numericUpDownGreen
            // 
            this.numericUpDownGreen.Increment = new decimal(new int[] {
            5,
            0,
            0,
            0});
            this.numericUpDownGreen.Location = new System.Drawing.Point(94, 577);
            this.numericUpDownGreen.Maximum = new decimal(new int[] {
            255,
            0,
            0,
            0});
            this.numericUpDownGreen.Name = "numericUpDownGreen";
            this.numericUpDownGreen.Size = new System.Drawing.Size(120, 20);
            this.numericUpDownGreen.TabIndex = 16;
            this.numericUpDownGreen.ValueChanged += new System.EventHandler(this.numericUpDownGreen_ValueChanged);
            // 
            // numericUpDownBlue
            // 
            this.numericUpDownBlue.Increment = new decimal(new int[] {
            5,
            0,
            0,
            0});
            this.numericUpDownBlue.Location = new System.Drawing.Point(94, 629);
            this.numericUpDownBlue.Maximum = new decimal(new int[] {
            255,
            0,
            0,
            0});
            this.numericUpDownBlue.Name = "numericUpDownBlue";
            this.numericUpDownBlue.Size = new System.Drawing.Size(120, 20);
            this.numericUpDownBlue.TabIndex = 17;
            this.numericUpDownBlue.ValueChanged += new System.EventHandler(this.numericUpDownBlue_ValueChanged);
            // 
            // numericUpDownRedUpper
            // 
            this.numericUpDownRedUpper.Increment = new decimal(new int[] {
            5,
            0,
            0,
            0});
            this.numericUpDownRedUpper.Location = new System.Drawing.Point(255, 514);
            this.numericUpDownRedUpper.Maximum = new decimal(new int[] {
            255,
            0,
            0,
            0});
            this.numericUpDownRedUpper.Name = "numericUpDownRedUpper";
            this.numericUpDownRedUpper.Size = new System.Drawing.Size(120, 20);
            this.numericUpDownRedUpper.TabIndex = 18;
            this.numericUpDownRedUpper.Value = new decimal(new int[] {
            255,
            0,
            0,
            0});
            this.numericUpDownRedUpper.ValueChanged += new System.EventHandler(this.numericUpDownRedUpper_ValueChanged);
            // 
            // numericUpDownGreenUpper
            // 
            this.numericUpDownGreenUpper.Increment = new decimal(new int[] {
            5,
            0,
            0,
            0});
            this.numericUpDownGreenUpper.Location = new System.Drawing.Point(255, 577);
            this.numericUpDownGreenUpper.Maximum = new decimal(new int[] {
            255,
            0,
            0,
            0});
            this.numericUpDownGreenUpper.Name = "numericUpDownGreenUpper";
            this.numericUpDownGreenUpper.Size = new System.Drawing.Size(120, 20);
            this.numericUpDownGreenUpper.TabIndex = 19;
            this.numericUpDownGreenUpper.Value = new decimal(new int[] {
            255,
            0,
            0,
            0});
            this.numericUpDownGreenUpper.ValueChanged += new System.EventHandler(this.numericUpDownGreenUpper_ValueChanged);
            // 
            // numericUpDownBlueUpper
            // 
            this.numericUpDownBlueUpper.Increment = new decimal(new int[] {
            5,
            0,
            0,
            0});
            this.numericUpDownBlueUpper.Location = new System.Drawing.Point(255, 629);
            this.numericUpDownBlueUpper.Maximum = new decimal(new int[] {
            255,
            0,
            0,
            0});
            this.numericUpDownBlueUpper.Name = "numericUpDownBlueUpper";
            this.numericUpDownBlueUpper.Size = new System.Drawing.Size(120, 20);
            this.numericUpDownBlueUpper.TabIndex = 20;
            this.numericUpDownBlueUpper.Value = new decimal(new int[] {
            255,
            0,
            0,
            0});
            this.numericUpDownBlueUpper.ValueChanged += new System.EventHandler(this.numericUpDownBlueUpper_ValueChanged);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1123, 721);
            this.Controls.Add(this.numericUpDownBlueUpper);
            this.Controls.Add(this.numericUpDownGreenUpper);
            this.Controls.Add(this.numericUpDownRedUpper);
            this.Controls.Add(this.numericUpDownBlue);
            this.Controls.Add(this.numericUpDownGreen);
            this.Controls.Add(this.numericUpDownRed);
            this.Controls.Add(this.buttonEdgeDetect);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.buttonTracking);
            this.Controls.Add(this.buttonCapture);
            this.Controls.Add(this.tableLayoutPanel1);
            this.Controls.Add(this.deviceBox);
            this.Controls.Add(this.buttonStart);
            this.Name = "Form1";
            this.Text = "Form1";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.Form1_FormClosing);
            this.Load += new System.EventHandler(this.Form1_Load);
            this.tableLayoutPanel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownRed)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownGreen)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownBlue)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownRedUpper)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownGreenUpper)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownBlueUpper)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button buttonStart;
        private System.Windows.Forms.ComboBox deviceBox;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel1;
        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.PictureBox pictureBox2;
        private System.Windows.Forms.Button buttonCapture;
        private System.Windows.Forms.Button buttonTracking;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Button buttonEdgeDetect;
        private System.Windows.Forms.NumericUpDown numericUpDownRed;
        private System.Windows.Forms.NumericUpDown numericUpDownGreen;
        private System.Windows.Forms.NumericUpDown numericUpDownBlue;
        private System.Windows.Forms.NumericUpDown numericUpDownRedUpper;
        private System.Windows.Forms.NumericUpDown numericUpDownGreenUpper;
        private System.Windows.Forms.NumericUpDown numericUpDownBlueUpper;
    }
}

