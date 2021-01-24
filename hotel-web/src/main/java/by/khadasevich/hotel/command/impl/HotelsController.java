package by.khadasevich.hotel.command.impl;

import by.khadasevich.hotel.command.Controller;
import by.khadasevich.hotel.dto.RoomTypeDto;
import by.khadasevich.hotel.dto.mappers.RoomTypeDtoMapper;
import by.khadasevich.hotel.entities.Hotel;
import org.apache.commons.lang3.math.NumberUtils;
import by.khadasevich.hotel.services.HotelService;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HotelsController implements Controller {
    private HotelService hotelService = SingletonBuilder.getInstanceImpl(HotelService.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        final long DEFAULT_ID = -1l;

        // del after debug
//        System.out.println("start HotelsController.execute");

        List<RoomTypeDto> roomTypeDtoList = null;
        if (req.getSession().getAttribute("roomTypeDtoList") == null) {
            //get roomTypeDtoList from DB,
            List<Hotel> hotelList = hotelService.getAll();
            roomTypeDtoList = new ArrayList<>();
            for (Hotel hotel : hotelList) {
                roomTypeDtoList.addAll(SingletonBuilder.getInstanceImpl(RoomTypeDtoMapper.class)
                                                       .mapAll(hotel.getId()));
            }
            // save in session roomTypeDtoList
            req.getSession().setAttribute("roomTypeDtoList", roomTypeDtoList);
        } else {
            //get roomTypeDtoList from session
            roomTypeDtoList = (List<RoomTypeDto>) req.getSession().getAttribute("roomTypeDtoList");
        }

        // take roomTypeDtoId from request param and check correct roomTypeDtoId
        long roomTypeDtoId = NumberUtils.toLong(req.getParameter("roomTypeDtoId"), DEFAULT_ID);
        RoomTypeDto roomTypeDto = null;
        if (roomTypeDtoList != null) {
            roomTypeDto = roomTypeDtoList.stream()
                                         .filter(p -> p.getRoomTypeId() == roomTypeDtoId)
                                         .findAny()
                                         .orElse(null);
        }

        if (roomTypeDto == null && roomTypeDtoId != DEFAULT_ID ) {
            //This is a BAD user - coll to FBR!!!
            req.setAttribute("message", "This is a BAD user - coll to FBR!!!");
        }

        if (roomTypeDto == null) {
            // del after debug
//            System.out.println("HotelsController.execute: No roomTypeDto, refer forward to MAIN_PAGE");

            // not exist such roomTypeDtoId, refer to MAIN_PAGE.
            req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);

            return;

        } else {
            // save roomTypeDto in session
            req.getSession().setAttribute("roomTypeDto", roomTypeDto);
        }

        // LogInn user, refer to booking - делать ордера!!!
        String contextPath = req.getContextPath();
        resp.sendRedirect(contextPath + "/frontController?command=orders");
    }
}
