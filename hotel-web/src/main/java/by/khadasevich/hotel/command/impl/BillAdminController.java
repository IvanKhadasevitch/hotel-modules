package by.khadasevich.hotel.command.impl;

import by.khadasevich.hotel.command.Controller;
import by.khadasevich.hotel.dto.BillDto;
import by.khadasevich.hotel.dto.mappers.BillDtoMapper;
import by.khadasevich.hotel.entities.Admin;
import by.khadasevich.hotel.entities.Bill;
import by.khadasevich.hotel.entities.enums.BillStatusType;
import by.khadasevich.hotel.services.BillService;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BillAdminController implements Controller {
    private BillService billService = SingletonBuilder.getInstanceImpl(BillService.class);
    private BillDtoMapper billDtoMapper = SingletonBuilder.getInstanceImpl(BillDtoMapper.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        final long DEFAULT_HOTEL_iD = -1L;

        // del after debug !!!
//        System.out.println("start BillAdminController");

        // check valid bill status, if invalid then  BillStatusType.UNPAID
        String billStatusStr = req.getParameter("billStatus") != null
                ? req.getParameter("billStatus")
                : null;

        // del after debug !!!
//        System.out.println("BillAdminController: req.getParameter(\"billStatus\")= " + billStatusStr);

        BillStatusType billStatus;
        try {
            billStatus = BillStatusType.valueOf(billStatusStr);
        } catch (NullPointerException | IllegalArgumentException e) {
            billStatus = BillStatusType.UNPAID;
        }
        // del after debug !!!
//        System.out.println("BillAdminController: set billStatus= " + billStatus);

        // take bills with status = billStatus from DB, & convert to billDtoList
        long hotelId = req.getSession().getAttribute("admin") == null
                ? DEFAULT_HOTEL_iD
                : ((Admin) req.getSession().getAttribute("admin")).getHotelId() ;
        List<Bill> billList = billService.getAllForBillStatus(billStatus);
        // transform Bills to BillDto, leave only with BillDto.hotelId = hotelId
        List<BillDto> billDtoList = billList.stream()
                                            .map(p -> billDtoMapper.map(p))
                                            .filter(p -> p.getHotelId() == hotelId)
                                            .collect(Collectors.toList());

        // save billDtoList in request
        req.setAttribute("billDtoList", billDtoList);

        //refer to MAIN_PAGE
        req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
    }
}
