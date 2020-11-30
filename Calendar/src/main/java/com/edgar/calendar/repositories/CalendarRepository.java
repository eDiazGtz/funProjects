package com.edgar.calendar.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.edgar.calendar.model.BookingCalendar;

@Repository
public interface CalendarRepository extends CrudRepository<BookingCalendar, Long> {

}
