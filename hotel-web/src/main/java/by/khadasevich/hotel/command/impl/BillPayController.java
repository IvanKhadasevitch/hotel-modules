package by.khadasevich.hotel.command.impl;

import by.khadasevich.hotel.command.Controller;
import by.khadasevich.hotel.entities.Bill;
import by.khadasevich.hotel.entities.User;
import org.apache.commons.lang3.math.NumberUtils;
import by.khadasevich.hotel.services.BillService;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BillPayController implements Controller {
    private BillService billService = SingletonBuilder.getInstanceImpl(BillService.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        // del after debug
//        System.out.println("start BillPayController.execute");

        // check valid request param
        long billDtoId = NumberUtils.toLong(req.getParameter("billDtoId"), -1L);
        Bill bill = billService.get(billDtoId);
        User user = req.getSession().getAttribute("user") != null
                ? (User) req.getSession().getAttribute("user")
                : null;
        boolean isBillPay = false;

        // try to pay bill
        if (user != null && bill != null && user.getId() == bill.getUserId()) {
            isBillPay = billService.billPay(bill.getId());
        }

        // safe billDtoId in request
        req.setAttribute("billDtoId", billDtoId);
        if (isBillPay) {
            // success message in request
            req.setAttribute("billPayMsg", "Paid bill with id= " + billDtoId);
        } else {
            // error message in request
            req.setAttribute("billPayErrorMsg", "Can't pay bill with id= " + billDtoId);
        }

        // refer to bills/main.jsp
        req.getRequestDispatcher( "/frontController?command=bills").forward(req, resp);

    }
}
