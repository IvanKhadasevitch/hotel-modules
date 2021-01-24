package by.khadasevich.hotel.command.impl;

import by.khadasevich.hotel.command.Controller;
import by.khadasevich.hotel.dto.BillDto;
import by.khadasevich.hotel.dto.mappers.BillDtoMapper;
import by.khadasevich.hotel.entities.Bill;
import by.khadasevich.hotel.entities.User;
import by.khadasevich.hotel.entities.enums.BillStatusType;
import by.khadasevich.hotel.services.BillService;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BillController implements Controller {
    private BillService billService = SingletonBuilder.getInstanceImpl(BillService.class);
    private BillDtoMapper billDtoMapper = SingletonBuilder.getInstanceImpl(BillDtoMapper.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        // del after debug
//        System.out.println("start BillController.execute");

        // check valid bill status, if invalid then  BillStatusType.UNPAID
        String billStatusStr = req.getParameter("billStatus") != null
                ? req.getParameter("billStatus")
                : null;
        // del after debug
//        System.out.println("BillController.execute req.getParameter(\"billStatus\")= " + billStatusStr);

        BillStatusType billStatus;
        try {
            billStatus = BillStatusType.valueOf(billStatusStr);
        } catch (NullPointerException | IllegalArgumentException e) {
            billStatus = BillStatusType.UNPAID;
        }
        // save billStatus in request
        req.setAttribute("billStatus", billStatus);

        // del after debug
//        System.out.println("BillController.execute set billStatus= " + billStatus);

        // take bills with status = billStatus for user with id from DB, & convert to billDtoList
        User user = (User) req.getSession().getAttribute("user");
        List<Bill> billList = billService.getAllForUserId(user.getId());
        final BillStatusType billStatusCheck = billStatus;
        // leave Bills with the status = billStatus
        billList = billList.stream()
                .filter(p -> billStatusCheck.equals(p.getStatus()))
                .collect(Collectors.toList());
        List<BillDto> billDtoList = billList.stream()
                .map(p -> billDtoMapper.map(p))
                .collect(Collectors.toList());

        // save billDtoList in request
        req.setAttribute("billDtoList", billDtoList);

        //refer to MAIN_PAGE
        req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
    }
}
