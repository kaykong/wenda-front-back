package top.kongk.wenda.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import top.kongk.wenda.common.QuestionCode;
import top.kongk.wenda.common.ServerResponse;
import top.kongk.wenda.common.UserValidatorUtil;
import top.kongk.wenda.dao.LoginTicketDao;
import top.kongk.wenda.dao.QuestionDao;
import top.kongk.wenda.model.Question;
import top.kongk.wenda.model.User;

import java.time.LocalDateTime;

/**
 * @author kkk
 */
@Service
public class QuestionService {

    private static final Logger log = LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    private QuestionDao questionDao;


    /**
     * 用户提交问题, 把问题插入数据库, 设置状态为待审核
     *
     * @author kongkk
     * @param question 问题
     * @param user 用户
     * @return top.kongk.wenda.common.ServerResponse
     */
    public ServerResponse addQuestion(Question question, User user) {
        /*
         * 对title和content进行敏感词过滤
         */
        //过滤html标签
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));


        question.setUserId(user.getId());
        question.setStatus(QuestionCode.TO_BE_AUDITED.getCode());
        question.setCreateDate(LocalDateTime.now());
        questionDao.addQuestion(question);
        /*
         * 问题的分类, 需要问题id, 还有一张表
         */
        return ServerResponse.createSuccessWithMsg("问题提交成功, 等待审核");
    }
}