package co.com.sofka.hexagonal.infraestructure.outputadapter;

import co.com.sofka.hexagonal.infraestructure.outputport.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Clase PostgresRepository
 * Generada para la implementación de un output point
 * en este caso realizando la comunicación con la BD Postgres.
 */
@Component
public class PostgresRepository implements EntityRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /***
     * Function save
     * @param reg
     * @return Registra el objeto pasado como parámetro y retorna dicho objeto.
     * @param <T>
     */
    @Override
    public <T> T save(T reg){
        Field[] entityFields = reg.getClass().getDeclaredFields();

        String[] fields = new String[entityFields.length];
        Object[] fieldValues = new Object[ entityFields.length];

        try{
            for ( int i =0; i < entityFields.length; i++){
                fields[i] = entityFields[i].getName();
                fieldValues[i] = reg.getClass()
                        .getMethod(
                                "get" + entityFields[i].getName().substring(0,1).toUpperCase() + entityFields[i].getName().substring(1))
                        .invoke(reg);
            }
        }catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e){
            e.printStackTrace();
        }

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ")
                .append(reg.getClass().getSimpleName())
                .append("(").append(String.join(",", fields)).append(")")
                .append(" VALUES ")
                .append("(").append(String.join(",", Collections.nCopies(fields.length, "?"))).append(")");

                jdbcTemplate.update(sql.toString(), fieldValues);

                return reg;
    }

    /***
     * Function getById
     * @param id
     * @param clazz
     * @return Un objeto del tipo pasado como parámetro, filtrado por ID
     * @param <T>
     */
    @Override
    public <T> T getById(String id, Class<T> clazz){
        List<T> list = jdbcTemplate.query("SELECT * FROM " + clazz.getSimpleName() + " WHERE id = ?",
                new LombokRowMapper<T>(clazz), id);

        if(!list.isEmpty())return list.get(0);

        return null;
    }

    /***
     * Function getAll
     * @param clazz
     * @return retorna lista con todos los objetos del tipo de clase pasado como parámetro
     * @param <T>
     */
    @Override
    public <T> List<T> getAll(Class<T> clazz){
        return jdbcTemplate.query("SELECT * FROM " + clazz.getSimpleName(), new LombokRowMapper<T>(clazz));
    }

    private class LombokRowMapper<T> implements RowMapper<T>{
        private Class<?> clazz = null;

        public LombokRowMapper(Class<?> clazz){
            this.clazz = clazz;
        }

        @Override
        public T mapRow(ResultSet rs, int rowNum) throws SQLException{
            try {
                Method builderMethod = clazz.getMethod("builder");

                if(builderMethod==null) return null;

                Object row = builderMethod.invoke(null);
                Method[] m = row.getClass().getDeclaredMethods();

                for(int i = 0; i < m.length; i++){
                    int pos = -1;

                    try{
                        pos = rs.findColumn(m[i].getName());
                    }catch (SQLException ex){
                        ex.printStackTrace();
                    }
                    if(pos != -1 ){
                        Object fieldValue= rs.getObject(pos);
                        m[i].invoke(row, fieldValue);
                    }

                }
                return (T) row.getClass().getMethod("buiild").invoke(row);

            }catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
            | NoSuchMethodException | SecurityException ex){
                ex.printStackTrace();
            }
            return null;
        }
    }
}
