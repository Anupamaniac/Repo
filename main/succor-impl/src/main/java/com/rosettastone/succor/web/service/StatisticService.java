package com.rosettastone.succor.web.service;

import com.rosettastone.succor.backgroundtask.MessageStatistic;
import com.rosettastone.succor.backgroundtask.impl.StatisticDao;
import com.rosettastone.succor.backgroundtask.impl.TaskDao;
import com.rosettastone.succor.backgroundtask.model.HistoryEntity;
import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.timertask.dao.ReportDao;
import com.rosettastone.succor.timertask.model.ReportEntityType;
import com.rosettastone.succor.timertask.model.StatisticBean;
import com.rosettastone.succor.web.dto.CounterDTO;
import com.rosettastone.succor.web.dto.StatisticDTO;
import com.rosettastone.succor.web.dto.TaskErrorDTO;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Service for generating statistic about messages.
 * It returns DTO objects because it used by flex UI part.
 */
public class StatisticService {

    private static final Log logger = LogFactory.getLog(StatisticService.class);

    private ReportDao reportDao;

    private TaskDao taskDao;

    private StatisticDao statisticDao;

    /**
     * This method loads last errors and statistic counters and wraps all data by DTO object for flex side.
     * @return
     * @throws Exception
     */
    public StatisticDTO getStatistic() throws Exception {
        try {
            StatisticDTO statisticDTO = new StatisticDTO();
            Date date = DateUtils.addMonths(new Date(), -1);

            List<CounterDTO> list = loadCounters(date);
            List<TaskErrorDTO> errors = loadErrors(date);

            statisticDTO.setCounters(list);
            statisticDTO.setErrors(errors);
            return statisticDTO;
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }
    }

    /**
     * Load history about last errors from database.
     * @param date
     * @return
     */
    private List<TaskErrorDTO> loadErrors(Date date) {
        // load last 500 entities
        List<HistoryEntity> history = taskDao.loadTaskErrors(date, 500);
        List<TaskErrorDTO> errors = new ArrayList<TaskErrorDTO>(history.size());
        // convert to DTO objects
        for (HistoryEntity entity : history) {
            TaskErrorDTO dto = new TaskErrorDTO();
            dto.setDate(entity.getDate());
            dto.setException(entity.getException());
            dto.setStacktrace(entity.getStacktrace());
            dto.setHistoryId(entity.getId());
            Task task = entity.getTask();
            dto.setTaskType(task.getType().toString());
            dto.setTaskId(task.getId());
            dto.setMessageId(task.getMessage().getId());
            dto.setMessage(task.getMessage().getMessage());
            errors.add(dto);
        }
        return errors;
    }

    /**
     * Load statistic counters and convert to DTO objects
     * @param date - the date from which we should generate statistic
     * @return
     */
    private List<CounterDTO> loadCounters(Date date) {
        List<CounterDTO> list = new ArrayList<CounterDTO>();
        StatisticBean statisticBean = reportDao.generateStatistic(date, new Date());
        MessageStatistic messageStatistic = statisticDao.loadMessageStatistic(date);
        list.add(new CounterDTO("Successfully processed messages", messageStatistic.getCompleted()));
        list.add(new CounterDTO("Messages processed with errors", messageStatistic.getCancelled()));
        list.add(new CounterDTO("Active messages", messageStatistic.getActive()));
        list.add(new CounterDTO("Total messages", messageStatistic.getTotal()));

        list.add(new CounterDTO("Emails to customer", statisticBean.get(ReportEntityType.EMAIL_CUSTOMER)));
        list.add(new CounterDTO("Email errors", statisticBean.get(ReportEntityType.EMAIL_ERROR)));
        list.add(new CounterDTO("Super tickets", statisticBean.get(ReportEntityType.SUPER_TIKET)));
        list.add(new CounterDTO("Institutional tickets", statisticBean.get(ReportEntityType.INSTITUTIONAL_TICKET)));
        list.add(new CounterDTO("Phone tickets", statisticBean.get(ReportEntityType.PHONE_TICKET)));
        list.add(new CounterDTO("Postal tickets", statisticBean.get(ReportEntityType.POSTAL_TICKET)));
        list.add(new CounterDTO("SMS to customer", statisticBean.get(ReportEntityType.SMS_CUSTOMER)));
        return list;
    }


    public void setReportDao(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    public void setStatisticDao(StatisticDao statisticDao) {
        this.statisticDao = statisticDao;
    }

    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }
}
