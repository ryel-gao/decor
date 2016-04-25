package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.CommentDao;
import com.bluemobi.decor.dao.CommentUpdateStatusDao;
import com.bluemobi.decor.entity.*;
import com.bluemobi.decor.service.*;
import com.bluemobi.decor.utils.ClassUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gaoll on 2015/10/21.
 */
@Service
@Transactional(readOnly = true)
public class CommentUpdateStatusServiceImpl implements CommentUpdateStatusService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private SeriesService seriesService;
   @Autowired
    private SceneService sceneService;
   @Autowired
    private GoodsService goodsService;
    @Autowired
    private CommentUpdateStatusDao commentUpdateStatusDao;


    @Override
    public List<CommentUpdateStatus> findAll() {
        return commentUpdateStatusDao.findAll();
    }

    @Override
    public Page<CommentUpdateStatus> find(int pageNum, int pageSize) {

        return commentUpdateStatusDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));

    }

    @Override
    public Page<CommentUpdateStatus> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public CommentUpdateStatus getById(int id) {
        return commentUpdateStatusDao.findOne(id);
    }

    @Override
    @Transactional
    public CommentUpdateStatus deleteById(int id) {
        CommentUpdateStatus commentUpdateStatus = getById(id);
        commentUpdateStatusDao.delete(commentUpdateStatus);
        return commentUpdateStatus;
    }

    @Override
    @Transactional
    public CommentUpdateStatus create(CommentUpdateStatus commentUpdateStatus) {
        commentUpdateStatusDao.save(commentUpdateStatus);
        return commentUpdateStatus;
    }

    @Override
    @Transactional
    public CommentUpdateStatus update(CommentUpdateStatus o) {
        CommentUpdateStatus dest = getById(o.getId());
        ClassUtil.copyProperties(dest, o);
        return commentUpdateStatusDao.save(dest);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }


    @Override
    @Transactional
    public void createOrUpdateBusiness(Integer userId,Integer objectId,String objectType) {
        List<CommentUpdateStatus> list = commentUpdateStatusDao.listByUserIdAndObjectIdAndObjectType(userId,objectId,objectType);
        // 新增
        if(list == null || list.size() == 0){
            CommentUpdateStatus commentUpdateStatus = new CommentUpdateStatus();
            User user = new User();
            user.setId(userId);
            commentUpdateStatus.setUser(user);
            commentUpdateStatus.setUpdateTime(new Date());
            commentUpdateStatus.setObjectId(objectId);
            commentUpdateStatus.setObjectType(objectType);
            create(commentUpdateStatus);
        }
        // 修改
        else {
            for (int i = 0; i < list.size(); i++) {
                CommentUpdateStatus  cus = list.get(i);
                cus.setUpdateTime(new Date());
                update(cus);
            }
        }
    }

    @Override
    public Page<CommentUpdateStatus> pcFindCommentPage(final Integer userId , Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<CommentUpdateStatus> page = commentUpdateStatusDao.findAll(new Specification<CommentUpdateStatus>() {
            @Override
            public Predicate toPredicate(Root<CommentUpdateStatus> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (userId!=null){
                    Predicate predicate = cb.equal(root.get("user").get("id").as(Integer.class), userId);
                    predicateList.add(predicate);
                }

                if (predicateList.size() > 0) {
                    result = cb.and(predicateList.toArray(new Predicate[]{}));
                }

                if (result != null) {
                    query.where(result);
                }

                return query.getRestriction();
            }
        }, pageRequest);
        List<CommentUpdateStatus> commentUpdateStatusList=page.getContent();
        for (CommentUpdateStatus commentUpdateStatus:commentUpdateStatusList){
            if (commentUpdateStatus.getObjectType().equals("series")){
                Series series = seriesService.getById(commentUpdateStatus.getObjectId());
                commentUpdateStatus.setObjectCover(series.getCover());
            }else if (commentUpdateStatus.getObjectType().equals("scene")){
                Scene scene=sceneService.getById(commentUpdateStatus.getObjectId());
                commentUpdateStatus.setObjectCover(scene.getImage());
            }else if (commentUpdateStatus.getObjectType().equals("goods")){
                Goods goods=goodsService.getById(commentUpdateStatus.getObjectId());
                commentUpdateStatus.setObjectCover(goods.getCover());
            }
            List<Comment> commentsList=commentDao.findCommentByObjectIdAndObjectType(commentUpdateStatus.getObjectId(), commentUpdateStatus.getObjectType());
            commentUpdateStatus.setCommentList(commentsList);
            // 查询评论列表
            List<Comment> commentList = commentUpdateStatus.getCommentList();

            // 查询评论列表的回复
            for (int i = 0; i < commentList.size(); i++) {
                //被回复评论
                List<Comment> replyList = commentDao.listReply(commentList.get(i).getId());

                List<Reply> reList = new ArrayList<Reply>();
                for (int j = 0; j < replyList.size(); j++) {
                    Reply reply = new Reply();
                    Comment c = replyList.get(j);
                    if(c.getUser() != null){
                        reply.setNickname(c.getUser().getNickname());
                        reply.setHeadImage(c.getUser().getHeadImage());
                    }
                    reply.setId(c.getId());
                    reply.setContent(c.getContent());
                    reply.setCreateTime(c.getCreateTime());
                    reply.setPraiseNum(c.getPraiseNum());
                    reList.add(reply);
                }
                commentList.get(i).setReplyList(reList);
            }
        }
        return page;
    }

    @Override
    public void deleteByObjectIdAndObjectType(int objId, String objType) {
        List<CommentUpdateStatus> list = commentUpdateStatusDao.findCommentUpdateStatusByObjectIdAndObjectType(objId, objType);
        for (CommentUpdateStatus commentUpdateStatus : list) {
            this.deleteById(commentUpdateStatus.getId());
        }
    }
    }

