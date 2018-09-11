package com.example.msi.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab sCrimeLab;
//    private List<Crime> mCrimes;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }
    private CrimeLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
//        mCrimes = new ArrayList<>();
    }

    public void addCrime(Crime c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeDBSchema.CrimeTable.NAME, null, values);
//        mCrimes.add(c);
    }

    public List<Crime> getCrimes() {
        return new ArrayList<>();
//        return mCrimes;
    }

    public Crime getCrime(UUID id){
//        for (Crime crime: mCrimes){
//            if (crime.getId().equals(id)){
//                return crime;
//            }
//        }
        return null;
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.update(CrimeDBSchema.CrimeTable.NAME, values,
                CrimeDBSchema.CrimeTable.Cols.UUID + " = ?",
                new String[] { uuidString });

    }
    private Cursor queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeDBSchema.CrimeTable.NAME,
                null, // columns - с null выбираются все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return cursor;

    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeDBSchema.CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeDBSchema.CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeDBSchema.CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeDBSchema.CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

        return values;
    }
}
