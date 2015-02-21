/**
 * @author attilax 老哇的爪子
	@since  2014-5-16 下午02:39:53$
 */
package com.attilax.fileup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.fileupload.FileItem;

import com.attilax.core;
import com.attilax.io.filex;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-5-16 下午02:39:53$
 */
public class FileItemImp implements FileItem {

//	protected String name;
	protected String name;
	public String Path;

	/* (non-Javadoc)
	 * @see org.apache.commons.fileupload.FileItem#delete()
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-16 下午02:42:07$
	 */
	@Override
	public void delete() {
		// attilax 老哇的爪子  下午02:42:07   2014-5-16 
		
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.fileupload.FileItem#get()
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-16 下午02:42:07$
	 */
	@Override
	public byte[] get() {
		// attilax 老哇的爪子  下午02:42:07   2014-5-16 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.fileupload.FileItem#getContentType()
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-16 下午02:42:07$
	 */
	@Override
	public String getContentType() {
		// attilax 老哇的爪子  下午02:42:07   2014-5-16 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.fileupload.FileItem#getFieldName()
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-16 下午02:42:07$
	 */
	@Override
	public String getFieldName() {
		// attilax 老哇的爪子  下午02:42:07   2014-5-16 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.fileupload.FileItem#getInputStream()
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-16 下午02:42:07$
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		// attilax 老哇的爪子  下午02:42:07   2014-5-16 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.fileupload.FileItem#getName()
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-16 下午02:42:07$
	 */
	@Override
	public String getName() {
		// attilax 老哇的爪子  下午02:42:07   2014-5-16 
		return name;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.fileupload.FileItem#getOutputStream()
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-16 下午02:42:07$
	 */
	@Override
	public OutputStream getOutputStream() throws IOException {
		// attilax 老哇的爪子  下午02:42:07   2014-5-16 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.fileupload.FileItem#getSize()
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-16 下午02:42:09$
	 */
	@Override
	public long getSize() {
		// attilax 老哇的爪子  下午02:42:09   2014-5-16 
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.fileupload.FileItem#getString()
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-16 下午02:42:09$
	 */
	@Override
	public String getString() {
		// attilax 老哇的爪子  下午02:42:09   2014-5-16 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.fileupload.FileItem#getString(java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-16 下午02:42:09$
	 */
	@Override
	public String getString(String arg0) throws UnsupportedEncodingException {
		// attilax 老哇的爪子  下午02:42:09   2014-5-16 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.fileupload.FileItem#isFormField()
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-16 下午02:42:09$
	 */
	@Override
	public boolean isFormField() {
		// attilax 老哇的爪子  下午02:42:09   2014-5-16 
		return false;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.fileupload.FileItem#isInMemory()
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-16 下午02:42:09$
	 */
	@Override
	public boolean isInMemory() {
		// attilax 老哇的爪子  下午02:42:09   2014-5-16 
		return false;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.fileupload.FileItem#setFieldName(java.lang.String)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-16 下午02:42:09$
	 */
	@Override
	public void setFieldName(String arg0) {
		// attilax 老哇的爪子  下午02:42:09   2014-5-16 
		
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.fileupload.FileItem#setFormField(boolean)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-16 下午02:42:09$
	 */
	@Override
	public void setFormField(boolean arg0) {
		// attilax 老哇的爪子  下午02:42:09   2014-5-16 
		
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.fileupload.FileItem#write(java.io.File)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-16 下午02:42:09$
	 */
	@Override
	public void write(File arg0) throws Exception {
		// attilax 老哇的爪子  下午02:42:09   2014-5-16 
		core.copy(Path, arg0.getPath());
		
	}
	//  attilax 老哇的爪子 下午02:39:53   2014-5-16   
}

//  attilax 老哇的爪子