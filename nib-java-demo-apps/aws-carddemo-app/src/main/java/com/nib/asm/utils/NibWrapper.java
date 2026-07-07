package com.nib.asm.utils;

import com.nib.asm.storage.NAsmField;
import com.nib.commons.storage.*;

public class NibWrapper {

    public static NChar toCharField(NAsmField field){
        Memory s = new Memory(field.getRefValue());
        NChar f = new NChar(s, field.getRefOffset(), field.getLength());
        return f;
    }

    public static NInt32 toInt32Field(NAsmField field){
        Memory s = new Memory(field.getRefValue());
        NInt32 f = new NInt32(s, field.getRefOffset());
        return f;
    }

    public static NPacked toPackedField(NAsmField field){
        Memory s = new Memory(field.getRefValue());
        NPacked f = new NPacked(s, field.getRefOffset(), field.getLength(), field.getDecimals(), true);
        return f;
    }



}
