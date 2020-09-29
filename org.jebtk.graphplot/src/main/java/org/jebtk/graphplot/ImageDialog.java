/**
 * Copyright 2016 Antony Holmes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jebtk.graphplot;

import java.awt.Frame;

import org.jebtk.modern.io.FileDialog;
import org.jebtk.modern.io.FileDialog.SaveDialog;
import org.jebtk.modern.io.FileDialog.SaveFileSelection;
import org.jebtk.modern.io.JpgGuiFileFilter;
import org.jebtk.modern.io.PdfGuiFileFilter;
import org.jebtk.modern.io.PngGuiFileFilter;
import org.jebtk.modern.io.SvgGuiFileFilter;

/**
 * The Class ImageDialog.
 */
public class ImageDialog {
	/**
	 * The Class ImageSaveDialog.
	 */
	public static class ImageSaveDialog {

		/** The m save. */
		private SaveDialog mSave;

		/**
		 * Instantiates a new image save dialog.
		 *
		 * @param frame the frame
		 */
		private ImageSaveDialog(Frame frame) {
			mSave = FileDialog.save(frame);
		}

		/**
		 * Image.
		 *
		 * @return the save file selection
		 */
		public SaveFileSelection getImage() {
			return mSave.filter(new SvgGuiFileFilter(), new PngGuiFileFilter(), new PdfGuiFileFilter(),
					new JpgGuiFileFilter());
		}
	}

	/**
	 * Save.
	 *
	 * @param frame the frame
	 * @return the image save dialog
	 */
	public static ImageSaveDialog save(Frame frame) {
		return new ImageSaveDialog(frame);
	}
}
