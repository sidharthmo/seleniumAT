package comdxc.vpc.automation.roughWork;

public class replaceSpace {

	public static void main(String[] args) {
		String a = "Chase Cooper Storage - 2017.11.14.xlsx";
		System.out.println(a);
		replaceSpace replaceSpace =new replaceSpace();
		replaceSpace.replaceWhitespace(a);
		System.out.println(a);

	}
	
	 private String replaceWhitespace(String str) {
		    if (str.contains(" ")) {
		    	System.out.println("testing");
		        str = str.replaceAll(" ", "%20");
		    }
		    return str;
	     }

}
