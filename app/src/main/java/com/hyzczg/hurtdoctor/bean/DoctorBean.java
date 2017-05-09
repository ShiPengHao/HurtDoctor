package com.hyzczg.hurtdoctor.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 医生类
 */

public class DoctorBean implements Parcelable {
    /**
     * 医生姓名
     */
    public String name;

    /**
     * 医生身份证号
     */
    public String cert;


    /**
     * 医生id
     */
    public String id;

    /**
     * 医生所属医院id
     */
    public String idHospital;

    /**
     * 医生所属科室id
     */
    public String idDepartment;

    /**
     * 医生职称
     */
    public String title;


    public DoctorBean(String name, String cert, String id, String idHospital, String idDepartment, String title) {
        this.name = name;
        this.cert = cert;
        this.id = id;
        this.idHospital = idHospital;
        this.idDepartment = idDepartment;
        this.title = title;
    }

    protected DoctorBean(Parcel in) {
        id = in.readString();
        cert = in.readString();
        name = in.readString();
        title = in.readString();
        idHospital = in.readString();
        idDepartment = in.readString();
    }

    public static final Creator<DoctorBean> CREATOR = new Creator<DoctorBean>() {
        @Override
        public DoctorBean createFromParcel(Parcel in) {
            return new DoctorBean(in);
        }

        @Override
        public DoctorBean[] newArray(int size) {
            return new DoctorBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(cert);
        dest.writeString(name);
        dest.writeString(title);
        dest.writeString(idHospital);
        dest.writeString(idDepartment);
    }
}
