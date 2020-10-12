package com.slavic.veles.base.exception;

import com.slavic.veles.base.response.EntryHeader;
import lombok.Getter;

@Getter
public class ApplyException extends RuntimeException{

    private EntryHeader entryHeader;

    public ApplyException(EntryHeader entryHeader) {
        super(entryHeader.getMessage());
        this.entryHeader = entryHeader;
    }
}
