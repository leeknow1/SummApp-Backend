package com.leeknow.summapp.schedule.schedules;

import com.leeknow.summapp.application.enums.ApplicationStatus;
import com.leeknow.summapp.application.enums.ApplicationType;
import com.leeknow.summapp.application.repository.ApplicationScheduleRepository;
import com.leeknow.summapp.log.enums.LogType;
import com.leeknow.summapp.log.service.LogService;
import com.leeknow.summapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
public class MonthlyReportSchedule extends AbstractScheduledTask {

    private final LogService log;
    private final UserRepository userRepository;
    private final ApplicationScheduleRepository applicationRepository;
    @Value("${report.files.directory}")
    private String path;

    @Override
    public void run() {
        File file = new File(path + "/" + new SimpleDateFormat("MM.dd").format(new Date()));
        if (file.mkdir()) {
            try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsolutePath() + "/report.xlsx")) {
                Sheet sheet = workbook.createSheet("report");
                sheet.setDefaultColumnWidth(60);

                Row row = sheet.createRow(0);
                row.createCell(0).setCellValue("Количиство пользователей всего");
                row.createCell(1).setCellValue(userRepository.countAllUsers());

                Row row1 = sheet.createRow(1);
                row1.createCell(0).setCellValue("Количиство пользователей за последний месяц");
                row1.createCell(1).setCellValue(userRepository.countAllUsersByLastMonth());

                Row row2 = sheet.createRow(2);
                row2.createCell(0).setCellValue("Количиство заявок всего");
                row2.createCell(1).setCellValue(applicationRepository.countAllApplications());

                Row row3 = sheet.createRow(3);
                row3.createCell(0).setCellValue("Количиство заявок за последний месяц");
                row3.createCell(1).setCellValue(applicationRepository.countAllApplicationsByLastMonth());

                Row row4 = sheet.createRow(4);
                row4.createCell(0).setCellValue("Количиство коротких заявок всего");
                row4.createCell(1).setCellValue(applicationRepository.countAllApplicationsByType(ApplicationType.SHORT.getId()));

                Row row5 = sheet.createRow(5);
                row5.createCell(0).setCellValue("Количиство коротких заявок за последний месяц");
                row5.createCell(1).setCellValue(applicationRepository.countAllApplicationsByLastMonthAndType(ApplicationType.SHORT.getId()));

                Row row6 = sheet.createRow(6);
                row6.createCell(0).setCellValue("Количиство длинных заявок всего");
                row6.createCell(1).setCellValue(applicationRepository.countAllApplicationsByType(ApplicationType.LONG.getId()));

                Row row7 = sheet.createRow(7);
                row7.createCell(0).setCellValue("Количиство длинных заявок за последний месяц");
                row7.createCell(1).setCellValue(applicationRepository.countAllApplicationsByLastMonthAndType(ApplicationType.LONG.getId()));

                Row row8 = sheet.createRow(8);
                row8.createCell(0).setCellValue("Количиство принятых заявок всего");
                row8.createCell(1).setCellValue(applicationRepository.countAllApplicationsByStatus(ApplicationStatus.APPROVED.getId()));

                Row row9 = sheet.createRow(9);
                row9.createCell(0).setCellValue("Количиство принятых заявок за последний месяц");
                row9.createCell(1).setCellValue(applicationRepository.countAllApplicationsByLastMonthAndStatus(ApplicationStatus.APPROVED.getId()));

                Row row10 = sheet.createRow(10);
                row10.createCell(0).setCellValue("Количиство отказанных заявок всего");
                row10.createCell(1).setCellValue(applicationRepository.countAllApplicationsByStatus(ApplicationStatus.REJECTED.getId()));

                Row row11 = sheet.createRow(11);
                row11.createCell(0).setCellValue("Количиство отказанных заявок за последний месяц");
                row11.createCell(1).setCellValue(applicationRepository.countAllApplicationsByLastMonthAndStatus(ApplicationStatus.REJECTED.getId()));

                Row row12 = sheet.createRow(12);
                row12.createCell(0).setCellValue("Количиство коротких, принятых заявок всего");
                row12.createCell(1).setCellValue(applicationRepository.countAllApplicationsByTypeAndStatus(ApplicationType.SHORT.getId(), ApplicationStatus.APPROVED.getId()));

                Row row13 = sheet.createRow(13);
                row13.createCell(0).setCellValue("Количиство коротких, принятых заявок за последний месяц");
                row13.createCell(1).setCellValue(applicationRepository.countAllApplicationsByLastMonthAndTypeAndStatus(ApplicationType.SHORT.getId(), ApplicationStatus.APPROVED.getId()));

                Row row14 = sheet.createRow(14);
                row14.createCell(0).setCellValue("Количиство длинных, принятых заявок всего");
                row14.createCell(1).setCellValue(applicationRepository.countAllApplicationsByTypeAndStatus(ApplicationType.LONG.getId(), ApplicationStatus.APPROVED.getId()));

                Row row15 = sheet.createRow(15);
                row15.createCell(0).setCellValue("Количиство длинных, принятых заявок за последний месяц");
                row15.createCell(1).setCellValue(applicationRepository.countAllApplicationsByLastMonthAndTypeAndStatus(ApplicationType.LONG.getId(), ApplicationStatus.APPROVED.getId()));

                Row row16 = sheet.createRow(16);
                row16.createCell(0).setCellValue("Количиство коротких, отказанных заявок всего");
                row16.createCell(1).setCellValue(applicationRepository.countAllApplicationsByTypeAndStatus(ApplicationType.SHORT.getId(), ApplicationStatus.REJECTED.getId()));

                Row row17 = sheet.createRow(17);
                row17.createCell(0).setCellValue("Количиство коротких, принятых заявок за последний месяц");
                row17.createCell(1).setCellValue(applicationRepository.countAllApplicationsByLastMonthAndTypeAndStatus(ApplicationType.SHORT.getId(), ApplicationStatus.REJECTED.getId()));

                Row row18 = sheet.createRow(18);
                row18.createCell(0).setCellValue("Количиство длинных, принятых заявок всего");
                row18.createCell(1).setCellValue(applicationRepository.countAllApplicationsByTypeAndStatus(ApplicationType.LONG.getId(), ApplicationStatus.REJECTED.getId()));

                Row row19 = sheet.createRow(19);
                row19.createCell(0).setCellValue("Количиство длинных, принятых заявок за последний месяц");
                row19.createCell(1).setCellValue(applicationRepository.countAllApplicationsByLastMonthAndTypeAndStatus(ApplicationType.LONG.getId(), ApplicationStatus.REJECTED.getId()));

                workbook.write(fileOutputStream);
            } catch (IOException e) {
                log.save(LogType.CRITICAL.getId(), e);
            }
            log.save(LogType.SYSTEM.getId(), "Зачада monthly-report завершена.");
        } else {
            log.save(LogType.SYSTEM.getId(), "Отчет за этот месяч уже существует. Зачада monthly-report завершается.");
        }
    }
}
