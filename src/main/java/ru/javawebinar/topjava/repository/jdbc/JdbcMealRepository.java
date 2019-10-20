package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepository implements MealRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Meal save(Meal meal, int userId) {

        jdbcTemplate.update("UPDATE meals SET user_id= ?, date_time= ?, description= ?, calories = ?",
                userId, meal.getDateTime(), meal.getDescription(), meal.getCalories());
        return meal;

    }

    @Override
    public boolean delete(int id, int userId) {
        jdbcTemplate.update("DELETE FROM meals Where id = ?", id);
        return true;
    }

    @Override
    public Meal get(int id, int userId) {
       Meal m = (Meal) jdbcTemplate.queryForObject("Select id, date_time, description, calories FROM meals Where id = ? limit 1",new Object[]{id}, new BeanPropertyRowMapper(Meal.class));
       return m;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("Select * FROM meals ", new BeanPropertyRowMapper(Meal.class));
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("Select * FROM meals Where date_time BETWEEN ? and ?",new Object[]{startDate,endDate}, new BeanPropertyRowMapper(Meal.class));
    }
}
