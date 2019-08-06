package com.nts.reservation.service.impl;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nts.reservation.dao.CommentDao;
import com.nts.reservation.dto.Comment;
import com.nts.reservation.dto.CommentInfo;
import com.nts.reservation.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private final CommentDao commentDao;

	@Autowired
	public CommentServiceImpl(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	@Override
	public List<CommentInfo> getCommentList(int productId) {
		return commentDao.selectCommentList(productId);
	}

	@Override
	public double getCommentAverage(List<CommentInfo> commentList) {
		double commentAverage = 0;

		for (CommentInfo comment : commentList) {
			commentAverage += comment.getScore();
		}

		return (commentList.size() == 0) ? 0 : commentAverage / commentList.size();
	}

	@Override
	public Comment getComment(int productId) {

		if (isValidProductId(productId) == false) {
			throw new IllegalArgumentException("productId가 음수입니다.");
		}

		Comment comment = new Comment();

		comment.setProductDescription(commentDao.selectProductDescription(productId));
		comment.setComments(getCommentList(productId));
		comment.setAverageScore(getCommentAverage(comment.getComments()));
		return comment;
	}

	private boolean isValidProductId(int productId) {
		return (productId > 0);
	}

}