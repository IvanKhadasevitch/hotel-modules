package by.khadasevich.hotel.dao;

import by.khadasevich.hotel.entities.Bill;
import by.khadasevich.hotel.entities.enums.BillStatusType;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface BillDao extends DAO<Bill> {
    /**
     * Get all Bills for user with id=userId.
     * @param userId is User id
     * @return List of Bills of User with userId
     * @throws SQLException
     */
    List<Bill> getAllForUserId(Serializable userId) throws SQLException;

    /**
     * Get all Bills with status=billStatus.
     * @param billStatus is Bill status.
     * @return List of Bills where status is billStatus
     * @throws SQLException
     */
    List<Bill> getAllForBillStatus(BillStatusType billStatus)
            throws SQLException;
}
