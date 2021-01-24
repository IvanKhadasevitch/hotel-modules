package by.khadasevich.hotel.services;

import by.khadasevich.hotel.entities.Bill;
import by.khadasevich.hotel.entities.enums.BillStatusType;

import java.io.Serializable;
import java.util.List;

public interface BillService {
    /**
     * Get Bill by id from database service.
     * @param billId is Bill id
     * @return Bill entity
     */
    Bill get(Serializable billId);

    /**
     * Save Bill in database service.
     * @param bill is Bill to save
     * @return saved Bill with id
     */
    Bill save(Bill bill);

    /**
     * Delete Bill with definite id from database service.
     * @param billId is Bill id
     * @return number of deleted rows
     */
    int delete(Serializable billId);

    /**
     * Pay Bill with definite id service.
     * @param billId is Bill id
     * @return true if operation success, otherwise false
     */
    boolean billPay(Serializable billId);

    /**
     * Get all Bills for definite User.
     * @param userId is User id
     * @return List of all Bills for definite User
     */
    List<Bill> getAllForUserId(Serializable userId);

    /**
     * Get all Bills with definite status service.
     * @param billStatus is Bill status
     * @return List of all Bills with definite status
     */
    List<Bill> getAllForBillStatus(BillStatusType billStatus);
}
