package com.sf.kycmanager.common;


public enum KYCUserEnum{
	
	SURNAME(2, "SURNAME(*)"), FIRST_NAME(3, "FIRSTNAME(*)"), OTHER_NAME(4, "OTHER NAME"), EMAIL(5, "EMAIL(*)"), GENDER(6, "GENDER(*)"), 
    MOBILE(7 , "MOBILE"), ROLE(8, "ROLE(*)"), ZONE(9, "ZONE");

	private int x;
    private String columnHeader;

    public String getColumnHeader() {
        return columnHeader;
    }

    public void setColumnHeader(String columnHeader) {
        this.columnHeader = columnHeader;
    }

    private KYCUserEnum(int x, String columnHeader) {
        this.x = x;
        this.columnHeader = columnHeader;
    }
    
    /**
     * Get the value of x
     *
     * @return the value of x
     */
    public int getX() {
        return x;
    }

    /**
     * Set the value of value
     *
     * @param x new value of x
     */
    public void setx(int x) {
        this.x = x;
    }

    public static String getColumnNameByColumnNumber(int number) {
        for (KYCUserEnum e : KYCUserEnum.values()) {
            if (number == e.getX()) {
                return e.name();
            }
        }
        return null;
    }
}