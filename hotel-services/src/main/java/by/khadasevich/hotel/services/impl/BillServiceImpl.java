package by.khadasevich.hotel.services.impl;

import by.khadasevich.hotel.services.BillService;
import by.khadasevich.hotel.dao.BillDao;
import by.khadasevich.hotel.dao.OrderDao;
import by.khadasevich.hotel.entities.Bill;
import by.khadasevich.hotel.entities.Order;
import by.khadasevich.hotel.entities.enums.OrderStatusType;
import by.khadasevich.hotel.services.ServiceException;
import by.khadasevich.hotel.singleton.SingletonBuilder;
import by.khadasevich.hotel.entities.enums.BillStatusType;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public final class BillServiceImpl
        extends AbstractService implements BillService {

    /**
     * BillDao singleton instance.
     */
    private final BillDao billDao =
            SingletonBuilder.getInstanceImpl(BillDao.class);
    /**
     * OrderDao singleton instance.
     */
    private final OrderDao orderDao =
            SingletonBuilder.getInstanceImpl(OrderDao.class);

    private BillServiceImpl() { }

    /**
     * Get Bill by id from database service.
     * @param billId is Bill id
     * @return Bill entity
     */
    @Override
    public Bill get(final Serializable billId) {
        try {
            return billDao.get(billId);
        } catch (SQLException e) {
            throw new ServiceException("Error getting Bill by id " + billId);
        }
    }

    /**
     * Save Bill in database service.
     * @param bill is Bill to save
     * @return saved Bill with id
     */
    @Override
    public  Bill save(final Bill bill) {
        try {
            this.startTransaction();
            Bill billSave = billDao.save(bill);
            this.commit();
            this.stopTransaction();
            return billSave;
        } catch (SQLException e) {
            rollback();
            throw new ServiceException("Error saving Bill " + bill);
        }
    }

    /**
     * Delete Bill with definite id from database service.
     * @param billId is Bill id
     * @return number of deleted rows
     */
    @Override
    public int delete(final Serializable billId) {
        int numberDeletedBills = 0;
        try {
            this.startTransaction();
            numberDeletedBills = billDao.delete(billId);
            this.commit();
            this.stopTransaction();
            return numberDeletedBills;
        } catch (SQLException e) {
            rollback();
            throw new ServiceException("Error deleting Bill by id " + billId);
        }
    }

    /**
     * Pay Bill with definite id service.
     * @param billId is Bill id
     * @return true if operation success, otherwise false
     */
    @Override
    public boolean billPay(final Serializable billId) {
        try {
            this.startTransaction();
            Bill bill = billDao.get(billId);
            if (bill == null) {
                // bill del from DB
                this.stopTransaction();

                return false;
            }
            if (BillStatusType.PAID.equals(bill.getStatus())) {
                // bill paid, nothing to do
                this.stopTransaction();

                return false;
            }
            Order order = orderDao.get(bill.getOrderId());
            if (order == null) {
                // order del from DB
                this.stopTransaction();

                return false;
            }
            // update bill status
            bill.setStatus(BillStatusType.PAID);
            billDao.update(bill);
            // update order status
            order.setStatus(OrderStatusType.PAID);
            orderDao.update(order);
            this.commit();
            this.stopTransaction();
            return true;
        } catch (SQLException e) {
            this.rollback();
            throw new ServiceException("Error pay Bill by id " + billId);
        }
    }

    /**
     * Get all Bills for definite User.
     * @param userId is User id
     * @return List of all Bills for definite User
     */
    @Override
    public List<Bill> getAllForUserId(final Serializable userId) {
        try {
            return billDao.getAllForUserId(userId);
        } catch (SQLException e) {
            throw new ServiceException("Error getting all Bills"
                    + " for user with id " + userId);
        }
    }

    /**
     * Get all Bills with definite status service.
     * @param billStatus is Bill status
     * @return List of all Bills with definite status
     */
    @Override
    public List<Bill> getAllForBillStatus(final BillStatusType billStatus) {
        try {
            return billDao.getAllForBillStatus(billStatus);
        } catch (SQLException e) {
            throw new ServiceException("Error getting all Bills"
                    + " with billStatus " + billStatus);
        }
    }

}
