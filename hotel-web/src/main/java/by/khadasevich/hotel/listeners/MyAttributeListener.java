package by.khadasevich.hotel.listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@WebListener
public class MyAttributeListener  implements HttpSessionAttributeListener {
    private Logger logger = LogManager.getLogger(MyAttributeListener.class.getName());
    private String counterAttr = "counter";

    public void attributeAdded(HttpSessionBindingEvent ev) {
        String currentAttributeName = ev.getName();
        String urlAttr = "URL";
        if (currentAttributeName.equals(counterAttr)) {
            Integer currentValueInt = (Integer) ev.getValue();
//            System.out.println("в Session добавлен счетчик=" + currentValueInt);
            logger.info("в Session добавлен счетчик=" + currentValueInt);
        } else if (currentAttributeName.equals(urlAttr)) {
            StringBuffer currentValueStr = (StringBuffer)ev.getValue();
//            System.out.println("в Session добавлен URL=" + currentValueStr);
            logger.info("в Session добавлен URL=" + currentValueStr);
        } else {
//            System.out.printf("добавлен новый атрибут=%s value=%s \n",currentAttributeName, ev.getValue() );
            String message = String.format("добавлен новый атрибут=%s value=%s",
                    currentAttributeName, ev.getValue());
            logger.info(message);
        }
    }
    public void attributeRemoved(HttpSessionBindingEvent ev) { }

    public void attributeReplaced(HttpSessionBindingEvent ev) {
        String currentAttributeName = ev.getName();
        // в случае изменений, произведенных со счетчиком,
        // выводит соответствующее сообщение
        if (currentAttributeName.equals(counterAttr)) {
            Integer currentValueInt = (Integer) ev.getValue();
//            System.out.println("В Session заменен cчетчик=" + currentValueInt);
            logger.info("В Session заменен cчетчик=" + currentValueInt);
        } else {
//            System.out.println(String.format("В Session заменен атрибут=%s value=%s ",
//                    currentAttributeName, ev.getValue()));
            String message = String.format(String.format("В Session заменен атрибут=%s value=%s ",
                    currentAttributeName, ev.getValue()));
            logger.info(message);
        }

    }
}
