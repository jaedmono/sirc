/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.movilidadbogota.core.dao;

import co.gov.movilidadbogota.core.entity.OperadoresPilaEntity;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mbogota
 */
@Repository
public class OperadoresPilaDAO extends AbstractDAO<OperadoresPilaEntity> {

    public OperadoresPilaDAO() {
        super(OperadoresPilaEntity.class);
    }
}
