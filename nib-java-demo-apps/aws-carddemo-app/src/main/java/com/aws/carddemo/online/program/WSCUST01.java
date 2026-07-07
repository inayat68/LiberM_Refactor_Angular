package com.aws.carddemo.online.program;

import com.aws.carddemo.common.copybook.Custrec;
import com.nib.commons.Context;
import com.nib.commons.annotations.ProgramStorage;
import com.nib.commons.exceptions.ContextException;
import com.nib.commons.storage.AbstractNField;
import com.nib.commons.storage.NGroup;
import com.nib.commons.storage.NInt16;
import com.nib.supernaut.AbstractOnlineProgram;

public class WSCUST01 extends AbstractOnlineProgram {
    public WSCUST01(Context rt) {
        super(rt);
    }

    class WorkingStorage extends NGroup {
        final Custrec.CustomerRecord customerRecord = new Custrec.CustomerRecord();
        final NInt16 wsLen = new NInt16();
    }

    @ProgramStorage
    final WorkingStorage WS = new WorkingStorage();

    @Override
    protected int procedure(AbstractNField dfhcommarea) throws ContextException {
        WS.wsLen.setValue(WS.customerRecord.length());
        if (dfheiblk.getEibcalen() < WS.wsLen.getInt()) {
            WS.wsLen.setValue(dfheiblk.getEibcalen());
        }
        WS.customerRecord.lowValues();
        WS.customerRecord.custId.setValue(dfhcommarea, 0, WS.wsLen.getInt());
        supernaut.read("CUSTDAT")
                .ridfld(WS.customerRecord.custId)
                .keylength(WS.customerRecord.custId.length())
                .into(WS.customerRecord)
                .length(WS.customerRecord.length())
                .exec();
        dfhcommarea.setValue(WS.customerRecord, 0, WS.wsLen.getInt());
        return RETURN_CODE;
    }

}
