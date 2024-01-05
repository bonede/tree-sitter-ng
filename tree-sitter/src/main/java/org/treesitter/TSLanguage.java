package org.treesitter;

public interface TSLanguage {
    long getPtr();

    default int version(){
        return TSParser.ts_language_version(this.getPtr());
    }

    default int fieldCount(){
        return TSParser.ts_language_field_count(this.getPtr());
    }

    default String fieldNameForId(int id){
        return TSParser.ts_language_field_name_for_id(this.getPtr(), id);
    }

    default int fieldIdForName(String name){
        return TSParser.ts_language_field_id_for_name(this.getPtr(), name);
    }

    default TSSymbolType symbolType(int symbol){
        int type = TSParser.ts_language_symbol_type(this.getPtr(), symbol);
        switch (type){
            case 0: return TSSymbolType.TSSymbolTypeRegular;
            case 1: return TSSymbolType.TSSymbolTypeAnonymous;
            case 2: return TSSymbolType.TSSymbolTypeAuxiliary;
            default: throw new TSException("Can't handle symbol type: %d" + type);
        }
    }

    default int symbolCount(){
        return TSParser.ts_language_symbol_count(this.getPtr());
    }

    default String symbolName(int symbol){
        return TSParser.ts_language_symbol_name(this.getPtr(), symbol);
    }

    default int symbolForName(String name, boolean isNamed){
        return TSParser.ts_language_symbol_for_name(this.getPtr(), name, isNamed);
    }

}
