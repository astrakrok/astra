package com.example.astraapi.service.impl;

import com.example.astraapi.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void execute(Runnable runnable) {
    if (runnable == null) {
      return;
    }
    runnable.run();
  }
}
