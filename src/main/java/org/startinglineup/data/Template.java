package org.startinglineup.data;

public class Template {
	
	private TemplateItem[] items;
	private TemplateItem[] headerItems;
	private int numItems = 0;
	private int numHeaderItems = 0;
	public static final String HEADER_STR = "[HEADER]";
	public static final String TARGET_STR = "[TARGET]";
	public static final String DONE_STR = "[DONE]";
	public static final String RECURSIVE_STR = "*";
	public static final String HIDDEN_STR = "$";
	public static final String DELIMITER_STR = ":";
	
	public Template(int numItems) {
		items = new TemplateItem[numItems];
		headerItems = new TemplateItem[numItems];
	}
	
	public void addItem(int index, String[] data) throws ClassNotFoundException {
		boolean isHeader = checkForHeader(data[0]);
		boolean isTarget = checkForTarget(data[0]);
		boolean isDone = checkForDone(data[0]);
		
		if (isHeader) {
			headerItems[index] = new TemplateItem(data[0], data[1], data[2], true, false, false);
			numHeaderItems++;
		} else if (isTarget) {
			items[index-numHeaderItems] = new TemplateItem("","", data[0], false, true, false);
			numItems++;
		} else if (isDone) {
			items[index-numHeaderItems] = new TemplateItem("","", data[0], false, false, true);
			numItems++;
		} else {
			items[index-numHeaderItems] = new TemplateItem(data[0], data[1], data[2]);
			numItems++;
		}
	}
	
	public TemplateItem[] getItems() {
		return items;
	}
	
	public TemplateItem getItem(int x) {
		return items[x];
	}
	
	public TemplateItem[] getHeaderItems() {
		return headerItems;
	}
	
	public TemplateItem getHeaderItem(int x) {
		return headerItems[x];
	}
	
	public boolean hasHeader() {
		return numHeaderItems > 0;
	}
	
	public int getNumItems() {
		return numItems;
	}
	
	public int getNumHeaderItems() {
		return numHeaderItems;
	}
	
	private boolean checkForHeader(String fieldName) {
		return fieldName.trim().toUpperCase().startsWith(HEADER_STR);
	}
	
	private boolean checkForTarget(String fieldName) {
		return fieldName.trim().toUpperCase().startsWith(TARGET_STR);
	}

	private boolean checkForDone(String fieldName) {
		return fieldName.trim().toUpperCase().startsWith(DONE_STR);
	}

	public class TemplateItem {
		
		private boolean isHeader;
		private boolean isTarget;
		private boolean isDone;
		private boolean isRecursive;
		private boolean isDerived;
		private boolean isHidden;
		private String columnName;
		private String fieldName;
		private String fieldClassName;
		private String subclassFieldName;
		
		private TemplateItem(String columnName, String fieldName, String fieldClassName) throws ClassNotFoundException {
			this(columnName, fieldName, fieldClassName, false, false, false);
		}

		private TemplateItem(String columnName, String fieldName, String fieldClassName, 
				boolean isHeader, boolean isTarget, boolean isDone) throws ClassNotFoundException {
			
			this.isHeader = isHeader;
			this.isTarget = isTarget;
			this.isDone = isDone;
			this.columnName = columnName.trim();
			setFieldName(fieldName);
			setFieldClassName(fieldClassName);
			setIsRecursive();
			setIsHidden();
			setIsDerived();
		}
		
		private void setFieldName(String fieldName) {
			if (fieldName.contains(DELIMITER_STR)) {
				String[] names = fieldName.split(DELIMITER_STR);
				this.fieldName = names[0].trim();
				this.subclassFieldName = names[1].trim();
			} else {
				this.fieldName = fieldName.trim();
				this.subclassFieldName = "";
			}
		}
		
		private void setFieldClassName(String fieldClassName) {
			if (isTarget) {
				this.fieldClassName = fieldClassName.trim().substring(
						TARGET_STR.length());
			} else {
				this.fieldClassName = fieldClassName.trim();
			}
		}
		
		private void setIsRecursive() {
			isRecursive = columnName.endsWith(RECURSIVE_STR);
		}
		
		private void setIsHidden() {
			isHidden = columnName.endsWith(HIDDEN_STR);
		}
		
		private void setIsDerived() {
			isDerived = (fieldClassName.contains(DELIMITER_STR));
		}

		public String getColumnName() {
			return columnName;
		}
		
		public String getFieldName() {
			return fieldName;
		}
		
		public String getSubclassFieldName() {
			return subclassFieldName;
		}
		
		public String getFieldClassName() {
			return fieldClassName;
		}
		
		public Class<?> getFieldClass() throws ClassNotFoundException {
			return Class.forName(fieldClassName);
		}
		
		public boolean isHeader() {
			return isHeader;
		}
		
		public boolean isTarget() {
			return isTarget;
		}
		
		public boolean isDone() {
			return isDone;
		}
		
		public boolean isHidden() {
			return isHidden;
		}
		
		public boolean isDerived() {
			return isDerived;
		}
		
		public boolean isRecursive() {
			return isRecursive;
		}
		
		public String toString() {
			return "COLUMN NAME["+columnName+"] FIELD NAME[" + 
		        fieldName + "] CLASS[" + fieldClassName + "] HEADER[" + isHeader + "]";
		}
	}
}
