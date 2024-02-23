export class GlobalConstants{
    public static genericError: string = "Something Went Wrong. Please Try Again";
    public static successMessage:string="Successfully Executed.";

    public static unAuthorized:string = "You are not authorized person to access this service. Please login with valid credentials.";
    public static productExistsError:string = "Product Already Exist" ;
    public static productAdded:string = "New Product Added Successfully!";

    //Regex
    public static nameRegex: string = "[a-zA-Z0-9 ]*";
    public static emailRegex: string = "[A-Za-z0-9._%-]+@[A-Za-z0-9._%-]+\\.[a-z]{2,3}"; 
	public static contactNumberRegEx : string = "^[e0-9]{10,10}$";


    //variables
    public static error:string = "Error";
    public static info:string= "Info";
    public static warning:string= "Warning";


}