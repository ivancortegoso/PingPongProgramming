package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.model.Commentary;
import com.solera.bankbackend.repository.ICommentaryRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentaryService extends CommonService<Commentary, ICommentaryRepository>{
}
