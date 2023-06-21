package com.example.buensabor.Services.Impl;

import com.example.buensabor.Models.Entity.CreditNote;
import com.example.buensabor.Repositories.CreditNoteRepository;
import com.example.buensabor.Services.CreditNoteService;
import org.springframework.stereotype.Service;

@Service
public class CreditNoteServiceImpl extends BaseServiceImpl<CreditNote,Long> implements CreditNoteService {

    private CreditNoteRepository creditNoteRepository;

    public CreditNoteServiceImpl(CreditNoteRepository creditNoteRepository) {
        super(creditNoteRepository);
        this.creditNoteRepository = creditNoteRepository;
    }

}
