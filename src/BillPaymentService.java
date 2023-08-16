
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import controller.ServiceController;

import java.util.List;

class BillPaymentService{  
    public static void main(String args[]){
        ServiceController controller = ServiceController.getInstance();
        System.out.println("Welcome to payment Service");
        System.out.println("Functional: CASH_IN, CREATE_BILL , LIST_BILL, PAY, DUE_DATE, SCHEDULE, LIST_PAYMENT");
    
        System.out.println(args[0]);
       switch(args[0]) {
            case "CASH_IN":
                controller.cashIn(Integer.parseInt(args[1]));
                break;
            case "CREATE_BILL":
                controller.createBill();;
                break;
            case "LIST_BILL":
                controller.listBill();
                break;
            case "PAY":
                List<Integer> payIds = new ArrayList<Integer>();
                for(int i = 1; i < args.length; i++) {
                    payIds.add(Integer.parseInt(args[i]));
                }
                controller.pay(payIds);
                break;
            case "DUE_DATE":
                controller.DueDay();
            case "SCHEDULE":
                controller.Schedule(Integer.parseInt(args[1]), LocalDate.parse(args[2], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                break;
            case "LIST_PAYMENT":
                controller.ListPayment();
                break;
            case " SEARCH_BILL_BY_PROVIDER":
                controller.SearchBillByProvider(args[1]);
                break;
            default:
                break;
        }
    }
} 