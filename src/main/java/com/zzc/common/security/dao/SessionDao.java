package com.zzc.common.security.dao;

import com.zzc.common.security.util.SerializableUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by wufan on 2015/12/11.
 */
public class SessionDao extends AbstractSessionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        String sql = "insert into SYS_SESSION(id, session, create_time) values(?,?, now())";
        jdbcTemplate.update(sql, sessionId, SerializableUtils.serialize(session));
        return session.getId();
    }


    @Override
    public void update(Session session) throws UnknownSessionException {
        if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
            // 如果会话过期/停止 没必要再更新了
            return;
        }
        String sql = "update SYS_SESSION set session=? where id=?";
        jdbcTemplate.update(sql, SerializableUtils.serialize(session), session.getId());
    }

    @Override
    public void delete(Session session) {
        String sql = "delete from SYS_SESSION where id=?";
        jdbcTemplate.update(sql, session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return null;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        String sql = "select session from SYS_SESSION where id=?";
        List<String> sessionStrList = jdbcTemplate.queryForList(sql, String.class, sessionId);
        if(sessionStrList.size() == 0) return null;
        return SerializableUtils.deserialize(sessionStrList.get(0));
    }
}
