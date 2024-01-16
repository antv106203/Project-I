package com.example.citizenmanagement.models;


import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Dialog;


import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatabaseConnection {
    private Connection connection;

    public DatabaseConnection() {
        String dbName = "QUANLYDANCU";
        String dbUser = "sa";
        String dbPassword = "123456789";
        String url = "jdbc:sqlserver://DESKTOP-0686QHH:1433;databaseName=" + dbName +
                ";IntegratedSecurity=false;encrypt=false;trustSeverCertificate=true;";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
        } catch (Exception e) {
            System.out.println("loi o dayyyy");
            throw new RuntimeException(e);
        }
    }
    private ResultSet executeQuery(String query) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }
    private void executeUpdate(String query) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /******************************************************************************************/
    // Citizen Manager Section - Phần Đăng Nhập
    public ResultSet getCitizenManagerData(String tenDangNhap, String matKhau) {

        String query = "SELECT * FROM NGUOIQUANLY\n" +
                "WHERE TENDANGNHAP = '" + tenDangNhap + "' AND MATKHAU = '" + matKhau +"'";
        return executeQuery(query);
    }

    public ResultSet checkCitizenManagerUsernameExisted(String tenDangNhap) {
        String query = "SELECT * FROM NGUOIQUANLY\n" +
                "WHERE TENDANGNHAP = '" + tenDangNhap + "'";
        return executeQuery(query);
    }

    public ResultSet checkCitizenManagerAccountExisted(String hoTen, String tenDangNhap, String soDienThoai) {
        String query = "SELECT * FROM NGUOIQUANLY\n" +
                "WHERE HOTEN = N'" + hoTen + "' AND TENDANGNHAP = '" + tenDangNhap + "' AND SODIENTHOAI = '" + soDienThoai + "'" ;
        return executeQuery(query);
    }
    public void updateCitizenManagerAccountPassword(String hoTen, String tenDangNhap, String soDienThoai, String maKhau) {
        String query = "UPDATE NGUOIQUANLY SET MATKHAU = '" + maKhau + "' \n" +
                "WHERE HOTEN = N'" + hoTen+ "' AND TENDANGNHAP = '" + tenDangNhap + "' AND SODIENTHOAI = '" + soDienThoai + "'";
        executeUpdate(query);
    }
    public void setCitizenManagerData(String hoTen, String tenDangNhap, String matKhau, String soDienThoai) {
        String query = "INSERT INTO NGUOIQUANLY(HOTEN, TENDANGNHAP, MATKHAU, SODIENTHOAI)\n" +
                        "VALUES (N'" + hoTen + "', '" + tenDangNhap + "', '" + matKhau + "', '" + soDienThoai + "')";

        executeUpdate(query);
    }

    /**************************************************************************************/
    // trang chủ - thống kê quản lý dân cư
    public ResultSet getNumberOfTamTru(int nam){
        String query = "SELECT COUNT(MAGIAYTAMTRU) FROM TAMTRU " +
                "WHERE " + nam + " BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY) ";
        return executeQuery(query);
    }
    public ResultSet getNumberOfTamVang(int nam){

        String query = "SELECT COUNT(MAGIAYTAMVANG) FROM TAMVANG WHERE " + nam + " BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY) ";
        return executeQuery(query);
    }
    public ResultSet getNumberOfNhanhKhau() {
        String query = "SELECT COUNT(MANHANKHAU) FROM NHANKHAU WHERE GHICHU LIKE N'tạm vắng' OR GHICHU IS NULL ";
        return executeQuery(query);
    }

    public ResultSet getNumberOfHoKhau(){
        String query = "select count(MAHOKHAU) from HOKHAU";
        return executeQuery(query);
    }

    public ResultSet getNumberOfTamTru(){
        String query = "SELECT COUNT(MAGIAYTAMTRU) FROM TAMTRU WHERE YEAR(GETDATE()) BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY) ";
        return executeQuery(query);
    }
    public ResultSet getNumberOfTamVang(){
        String query = "SELECT COUNT(MAGIAYTAMVANG) FROM TAMVANG WHERE YEAR(GETDATE()) BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY) ";
        return executeQuery(query);
    }

    public ResultSet getNumberOfNhanKhauNam(){
        String query = "select count(MANHANKHAU) from NHANKHAU where GIOITINH = 1 AND (GHICHU LIKE N'tạm vắng' OR GHICHU IS NULL)";
        return executeQuery(query);
    }
    public ResultSet getNumberOfNhanKhauNu(){
        String query = "select count(MANHANKHAU) from NHANKHAU where GIOITINH = 0 AND (GHICHU LIKE N'tạm vắng' OR GHICHU IS NULL)";
        return executeQuery(query);
    }

    public ResultSet getNumberOfNhanKhauDuoi3Tuoi(){
        String query = "select count(MANHANKHAU) \n" +
                "from NHANKHAU\n" +
                "where YEAR(GETDATE()) - YEAR(NGAYSINH) < 3 AND YEAR(GETDATE()) - YEAR(NGAYSINH) >= 0 AND (GHICHU LIKE N'tạm vắng' OR GHICHU IS NULL)";
        return executeQuery(query);
    }
    public ResultSet getNumberOfNhanKhauTu3Den10Tuoi(){
        String query = "select count(MANHANKHAU) \n" +
                "from NHANKHAU\n" +
                "where YEAR(GETDATE()) - YEAR(NGAYSINH) >= 3 AND YEAR(GETDATE()) - YEAR(NGAYSINH) < 10 AND (GHICHU LIKE N'tạm vắng' OR GHICHU IS NULL)";
        return executeQuery(query);
    }

    public ResultSet getNumberOfNhanKhauTu10Den18Tuoi(){
        String query = "select count(MANHANKHAU) \n" +
                "from NHANKHAU\n" +
                "where YEAR(GETDATE()) - YEAR(NGAYSINH) >= 10 AND YEAR(GETDATE()) - YEAR(NGAYSINH) < 18 AND (GHICHU LIKE N'tạm vắng' OR GHICHU IS NULL)";
        return executeQuery(query);
    }

    public ResultSet getNumberOfNhanKhauTu18Den60Tuoi(){
        String query = "select count(MANHANKHAU) \n" +
                "from NHANKHAU\n" +
                "where YEAR(GETDATE()) - YEAR(NGAYSINH) >= 18 AND YEAR(GETDATE()) - YEAR(NGAYSINH) < 60 AND (GHICHU LIKE N'tạm vắng' OR GHICHU IS NULL)";
        return executeQuery(query);
    }
    public ResultSet getNumberOfNhanKhauTren60Tuoi(){
        String query = "select count(MANHANKHAU) \n" +
                "from NHANKHAU\n" +
                "where YEAR(GETDATE()) - YEAR(NGAYSINH) >= 60 AND (GHICHU LIKE N'tạm vắng' OR GHICHU IS NULL)";
        return executeQuery(query);
    }
    public ResultSet getNamHienTai(){
        String query = "select YEAR(GETDATE())";
        return executeQuery(query);
    }
    public ResultSet getHoKhauOfNamHienTai(){
        String query = "SELECT COUNT(MAHOKHAU)\n" +
                "FROM HOKHAU";
        return executeQuery(query);
    }

    public ResultSet getHoKhauOfNam(int nam){
        String query = "SELECT COUNT(MAHOKHAU)\n" +
                "FROM HOKHAU\n" +
                "WHERE " + nam + " > YEAR(NGAYTAO)";
        return executeQuery(query);
    }

    public ResultSet getTamTruOfThangVaNam(int thang,int nam){
        String query = "SELECT COUNT(MAGIAYTAMTRU)\n" +
                "FROM TAMTRU\n" +
                "WHERE " + thang + " BETWEEN MONTH(TUNGAY) AND MONTH(DENNGAY)" + "\n" +
                "AND " + nam + " BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY) ";
        return executeQuery(query);
    }

    public ResultSet getTamTruViLyDoHocTap(int nam){
        String query = "SELECT COUNT(MAGIAYTAMTRU)\n" +
                "FROM TAMTRU\n" +
                "WHERE LYDO LIKE N'%Học tập%' AND " + nam + " BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY) ";
       return executeQuery(query);
    }
    public ResultSet getTamTruViLyDoLamViec(int nam){
        String query = "SELECT COUNT(MAGIAYTAMTRU)\n" +
                "FROM TAMTRU\n" +
                "WHERE LYDO LIKE N'%Làm việc%' AND " + nam + " BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY) ";
        return executeQuery(query);
    }

    public ResultSet getTamTruViLyDoSucKhoe(int nam){
        String query = "SELECT COUNT(MAGIAYTAMTRU)\n" +
                "FROM TAMTRU\n" +
                "WHERE LYDO LIKE N'%sức khỏe%' AND " + nam + " BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY)";
        return executeQuery(query);
    }

    public ResultSet getTamVangOfThangVaNam(int thang,int nam){
        String query = "SELECT COUNT(MAGIAYTAMVANG)\n" +
                "FROM TAMVANG\n" +
                "WHERE (YEAR(TUNGAY) = " + nam + " AND MONTH(TUNGAY) <= " + thang + ") OR (YEAR(DENNGAY) = " + nam + " AND MONTH(DENNGAY) >= " + thang + ")\n" +
                "\tOR (YEAR(TUNGAY) < " + nam + " AND YEAR(DENNGAY) > " + nam + ")" ;

        return executeQuery(query);
    }

    public ResultSet getTamVangViLyDoHocTap(int nam){
        String query = "SELECT COUNT(MAGIAYTAMVANG) FROM TAMVANG WHERE LYDO LIKE N'%Học tập%' AND " + nam + " BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY)" ;

        return executeQuery(query);
    }

    public ResultSet getTamVangViLyDoLamViec(int nam){
        String query = "SELECT COUNT(MAGIAYTAMVANG) FROM TAMVANG   WHERE LYDO LIKE N'%Làm việc%' AND " + nam + " BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY)"  ;
        return executeQuery(query);
    }

    public ResultSet getTamVangViLyDoSucKhoe(int nam){
        String query = "SELECT COUNT(MAGIAYTAMVANG) FROM TAMVANG  WHERE LYDO LIKE N'%sức Khoẻ%' AND " + nam + " BETWEEN YEAR(TUNGAY) AND YEAR(DENNGAY)" ;
        return executeQuery(query);
    }



    //Nhân khẩu
    public int addNhanKhau (String hoTen, String CCCD, String ngaySinh, int gioiTinh, String noiSinh, String nguyenQuan,String danToc, String tonGiao, String quocTich, String noiThuongTru, String ngheNghiep, String ghiChu ){
        int thanhcong = 0;
        String querry = "insert into NHANKHAU (HOTEN, SOCANCUOC, NGAYSINH, GIOITINH, NOISINH, NGUYENQUAN, DANTOC, TONGIAO, QUOCTICH, NOITHUONGTRU, NGHENGHIEP, NGAYTAO, GHICHU ) " +
                " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement pre = connection.prepareStatement(querry);
            pre.setNString(1,hoTen); pre.setString(2,CCCD);
            pre.setString(3,ngaySinh); pre.setInt(4,gioiTinh);
            pre.setNString(5,noiSinh); pre.setNString(6,nguyenQuan);
            pre.setNString(7,danToc); pre.setNString(8,tonGiao);
            pre.setNString(9,quocTich);
            pre.setNString(10,noiThuongTru); pre.setNString(11,ngheNghiep);
            pre.setDate(12, Date.valueOf(LocalDate.now().toString())); pre.setNString(13,ghiChu);
            thanhcong = pre.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println("Lỗi thêm nhân khẩu");
            throw new RuntimeException(e);
        }
        return thanhcong;
    }

    public int addTamtru(String hoTen, String CCCD, String ngaySinh, int gioiTinh, String noiSinh, String nguyenQuan, String danToc, String tonGiao, String quocTich, String noiThuongTru, String ngheNghiep, String sdt, Date ngayDen, Date ngayDi, String liDo ) {
        int thanhcong = 0;
        String que = "EXEC INSERT_TAMTRU ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
        try{
            PreparedStatement pre = connection.prepareStatement(que);
            pre.setNString(1,hoTen); pre.setString(2,CCCD);
            pre.setString(3, ngaySinh); pre.setInt(4,gioiTinh);
            pre.setNString(5,noiSinh); pre.setNString(6,nguyenQuan);
            pre.setNString(7,danToc); pre.setNString(8,tonGiao);
            pre.setNString(9,quocTich);
            pre.setNString(10,noiThuongTru); pre.setNString(11,ngheNghiep);
            pre.setString(12, sdt);
            pre.setDate(13, ngayDen); pre.setDate(14,ngayDi);
            pre.setNString(15,liDo);
            thanhcong = pre.executeUpdate();
        }catch(Exception e) {
            System.out.println("Lỗi thêm nhân khẩu");
            throw new RuntimeException(e);
        }

        return thanhcong;
    }

    public int addKhaitu(String maNguoiKhai, String maNguoiMat, Date ngayMat, String liDo) {
        int thanhcong = 0;
        String que = "INSERT INTO KHAITU (MANHANKHAUNGUOIKHAI, MANHANKHAUNGUOICHET, NGAYKHAI, NGAYCHET, LYDOCHET) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = connection.prepareStatement(que);
            pre.setString(1, maNguoiKhai);
            pre.setString(2, maNguoiMat);
            pre.setDate(3, Date.valueOf(LocalDate.now()));
            pre.setDate(4, ngayMat);
            pre.setNString(5, liDo);
            thanhcong = pre.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi khai tử");
            throw new RuntimeException(e);
        }
        return thanhcong;
    }

    public int capnhatNhanKhau (String string){
        int thanhcong = 0;
        String querry = "update NHANKHAU SET NGAYTAO = ? Where SOCANCUOC = ?";
        try{
            PreparedStatement pre = connection.prepareStatement(querry);
            pre.setDate(1,Date.valueOf(LocalDate.now().toString()));
            pre.setString(2,string);
            thanhcong = pre.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println("Lỗi câpj nhật khẩu");
            throw new RuntimeException(e);
        }
        return thanhcong;
    }

    public int capnhatNhanKhauShow (String hoten, Date ngaysinh, int Gioitinh, String noisinh, String nguyenquan, String dantoc, String tongiao, String quoctich, String noithuongtru, String nghenghiep, String ghichu,String manhankhau){
        int thanhcong = 0;
        String querry = "update NHANKHAU SET HOTEN = ? , NGAYSINH = ? , GIOITINH = ? , NOISINH = ? , NGUYENQUAN =? , DANTOC =? , TONGIAO = ? , QUOCTICH =? , NOITHUONGTRU = ? , NGHENGHIEP = ?, GHICHU =? Where MANHANKHAU = ?";
        try{
            PreparedStatement pre = connection.prepareStatement(querry);
            pre.setNString(1,hoten);
            pre.setDate(2, ngaysinh);
            pre.setInt(3,Gioitinh);
            pre.setNString(4,noisinh);
            pre.setNString(5,nguyenquan);
            pre.setNString(6,dantoc);
            pre.setNString(7,tongiao);
            pre.setNString(8,quoctich);
            pre.setNString(9,noithuongtru);
            pre.setNString(10,nghenghiep);
            pre.setNString(11,ghichu);
            pre.setString(12,manhankhau);
            thanhcong = pre.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println("Lỗi câpj nhật khẩu");
            throw new RuntimeException(e);
        }
        return thanhcong;
    }
    // Nhân khâur
    public ResultSet KiemTraXemMaNhanKhauDaTonTaiTrongTamVang(int manhankhau){

        ResultSet resultSet = null;
        String query = "SELECT COUNT(MANHANKHAU) FROM TAMVANG WHERE MANHANKHAU =" + manhankhau;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;

    }
    public void dangKiTamVang(int maNhanKhau, String noiTamTru,String tuNgay, String denNgay,String lyDo){
        String dangkitamvang = "EXEC INSERT_TAM_VANG ?, ?, ?, ?,?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(dangkitamvang);
            preparedStatement.setInt(1,maNhanKhau);

            preparedStatement.setString(3,tuNgay);

            preparedStatement.setString(4,denNgay);

            preparedStatement.setNString(5,lyDo);

            if(noiTamTru.isEmpty())
                preparedStatement.setNString(2,null);
            else
                preparedStatement.setNString(2,noiTamTru);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet KiemTraMaNhanKhauCoTonTaiHayKhong(int manhankhau){
        ResultSet resultSet = null;
        String query = "SELECT COUNT(MANHANKHAU) FROM NHANKHAU WHERE MANHANKHAU =" + manhankhau;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet truyvanlistNhanKhau( String manhankhau) {
        ResultSet resultSet = null;
        String que = "SELECT HOTEN, SOCANCUOC, NGAYSINH, GIOITINH, NOISINH, NGUYENQUAN, DANTOC, TONGIAO, QUOCTICH, NOITHUONGTRU, NGHENGHIEP, NGAYTAO, GHICHU FROM NHANKHAU WHERE MANHANKHAU = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(que);
            preparedStatement.setString(1,    manhankhau );
            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public int xoa_tam_tru(String MaNhanKhau) {
        if(!MaNhanKhau.isEmpty()) {
            String query = "Delete TAMTRU where MANHANKHAU = ?";
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, MaNhanKhau);
                statement.executeUpdate();
                return 1;
            } catch (Exception e) {
                return 0;
            }
        }
        else
            return 0;
    }

    public ResultSet nhanKhau_timkiem(String string) {
        ResultSet resultSet = null;
        String querry = " select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU from NHANKHAU where MANHANKHAU like ? or SOCANCUOC like ? or HOTEN like ?";
        try {
            PreparedStatement preparedstatement = connection.prepareStatement(querry);
            preparedstatement.setString(1, "%" + string + "%");
            preparedstatement.setString(2, "%" + string + "%");
            preparedstatement.setNString(3, "%" + string + "%");
            resultSet = preparedstatement.executeQuery();
        }
        catch(Exception e) {
            System.out.println("Lỗi tìm kiếm");
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet nhanKhau_timkiemTheoMaNhanKhau(String dieukien) {
        ResultSet resultSet = null;
        String querry = " select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU from NHANKHAU where MANHANKHAU like ?";
        try {
            PreparedStatement preparedstatement = connection.prepareStatement(querry);
            preparedstatement.setNString(1, "%" + dieukien + "%");
            resultSet = preparedstatement.executeQuery();
        }
        catch(Exception e) {
            System.out.println("Lỗi tìm kiếm theo mã nhân khẩu");
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet nhanKhau_timkiemTheoSoCCCD(String dieukien) {
        ResultSet resultSet = null;
        String querry = " select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU from NHANKHAU where SOCANCUOC like ?";
        try {
            PreparedStatement preparedstatement = connection.prepareStatement(querry);
            preparedstatement.setNString(1, "%" + dieukien + "%");
            resultSet = preparedstatement.executeQuery();
        }
        catch(Exception e) {
            System.out.println("Lỗi tìm kiếm theo số CCCD");
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet nhanKhau_timkiemTheoHoVaTen(String dieukien) {
        ResultSet resultSet = null;
        String querry = " select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU from NHANKHAU where HOTEN like ?";
        try {
            PreparedStatement preparedstatement = connection.prepareStatement(querry);
            preparedstatement.setNString(1, "%" + dieukien + "%");
            resultSet = preparedstatement.executeQuery();
        }
        catch(Exception e) {
            System.out.println("Lỗi tìm kiếm theo Họ Và Tên");
            throw new RuntimeException(e);
        }
        return resultSet;
    }
    public ResultSet truyvan() {
        ResultSet resultSet = null;
        String querry = " select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU from NHANKHAU;";
        try{
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(querry);
        }
        catch(Exception e) {

        }
        return resultSet;
    }

    public ResultSet truyvanTamTru() {
        ResultSet resultSet = null;
        String querry = " select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU from NHANKHAU where GHICHU like N'%tạm trú%';";
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(querry);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }
    /***********************************************************************************/
    // Hộ khẩu

    public int addHoKhau(String ma_ch, String diachi, String ghichu){
        if(!ma_ch.isEmpty() && !diachi.isEmpty()) {
            String query = "EXEC INSERT_HOKHAU ?, ?, ?, ?";
            try {
                PreparedStatement statement = connection.prepareStatement(query);

                statement.setString(1, ma_ch);
                statement.setNString(2, diachi);
                statement.setString(3,LocalDate.now().toString());
                if(ghichu.isEmpty())
                    statement.setNString(4,null);
                else
                    statement.setNString(4, ghichu);

                statement.executeUpdate();
                return 1;
            } catch (Exception e) {
                System.out.println("loi o addHoKhau");
//                throw new RuntimeException(e);
                return 0;
            }
        }
        else
            return 0;
    }
    public ResultSet getDanhSachHoKhau(){
        String query = "select * from HOKHAU";
        return executeQuery(query);
    }
    public ResultSet timKiem(String dieukien){
        ResultSet resultSet=null;
        String query = "SELECT * FROM HOKHAU\n" +
                "WHERE MAHOKHAU LIKE ? OR TENCHUHO LIKE ? OR DIACHI LIKE ?";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + dieukien + "%");
            statement.setNString(2, "%" + dieukien + "%");
            statement.setNString(3, "%" + dieukien + "%");
            resultSet = statement.executeQuery();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet timKiemTheoMaHoKhau(String dieukien){
        ResultSet resultSet=null;
        String query = "SELECT * FROM HOKHAU\n" +
                "WHERE MAHOKHAU LIKE ? ";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + dieukien + "%");
            resultSet = statement.executeQuery();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return resultSet;
    }
    public ResultSet timKiemTheoChuHoKhau(String dieukien){
        ResultSet resultSet=null;
        String query = "SELECT * FROM HOKHAU\n" +
                "WHERE TENCHUHO LIKE ? ";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setNString(1, "%" + dieukien + "%");
            resultSet = statement.executeQuery();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet timKiemTheoDiaChiHoKhau(String dieukien){
        ResultSet resultSet=null;
        String query = "SELECT * FROM HOKHAU\n" +
                "WHERE DIACHI LIKE ?";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setNString(1, "%" + dieukien + "%");
            resultSet = statement.executeQuery();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return resultSet;
    }
    public ResultSet lay_ho_khau(String ma_chu_ho){
        String query = "select * from HOKHAU WHERE IDCHUHO = " + ma_chu_ho;
        return executeQuery(query);
    }
    public ResultSet getMaHoKhau(String maChuHo) {
        String query = "Select mahokhau from thanhviencuaho where manhankhau = " + maChuHo;

        return executeQuery(query);
    }
    public int capNhatHoKhau(String idHoKhau, String maChuHo, String diaChi, String ghiChu){

        try {
            String capnhat = "update HOKHAU set IDCHUHO=?, DIACHI=?, GHICHU=?, tenchuho=? where MAHOKHAU=?";
            PreparedStatement preparedStatement = connection.prepareStatement(capnhat);
            preparedStatement.setString(1,maChuHo);
            preparedStatement.setNString(2,diaChi);
            if(ghiChu.isEmpty())
                preparedStatement.setNString(3,null);
            else
                preparedStatement.setNString(3, ghiChu);
            preparedStatement.setString(5,idHoKhau);


            String lay_ten_chu="select * from nhankhau where manhankhau="+maChuHo;
            Statement statement = connection.createStatement();
            ResultSet resultSet1 = statement.executeQuery(lay_ten_chu);
            if(resultSet1.isBeforeFirst()){
                resultSet1.next();
                preparedStatement.setString(4,resultSet1.getNString(2));
            }
            else {
                preparedStatement.setString(4,null);
            }
            preparedStatement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    public int xoaHoKhau(String maHoKhau) {
        String query = "DELETE from HOKHAU\n" +
                "WHERE MAHOKHAU = " + maHoKhau;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return  1;
        } catch (SQLException e) {
            return 0;
            //throw new RuntimeException(e);
        }
    }
    public ResultSet getDanhSachTamVang() {
        ResultSet resultSet = null;
        String query= "SELECT * FROM TAMVANG TV , NHANKHAU NK WHERE TV.MANHANKHAU = NK.MANHANKHAU";
        Statement statement;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet getDanhSachTamTru() {
        ResultSet resultSet = null;
        String query= "SELECT * FROM TAMTRU TT , NHANKHAU NK WHERE TT.MANHANKHAU = NK.MANHANKHAU";
        Statement statement;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet getDanhSachTamTruConHieuLuc() {
        ResultSet resultSet = null;
        String query= "SELECT * FROM TAMTRU TT , NHANKHAU NK WHERE TT.MANHANKHAU = NK.MANHANKHAU AND CONVERT(VARCHAR,DENNGAY,112) >=  CONVERT(VARCHAR,GETDATE(),112)";
        Statement statement;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet getDanhSachTamTruHetHieuLuc() {
        ResultSet resultSet = null;
        String query= "SELECT * FROM TAMTRU TT , NHANKHAU NK WHERE TT.MANHANKHAU = NK.MANHANKHAU AND CONVERT(VARCHAR,DENNGAY,112) < CONVERT(VARCHAR,GETDATE(),112)";
        Statement statement;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }


    public ResultSet getDanhSachTamVangConHieuLuc() {
        ResultSet resultSet = null;
        String query= "SELECT * FROM TAMVANG TV , NHANKHAU NK WHERE TV.MANHANKHAU = NK.MANHANKHAU AND CONVERT(VARCHAR,DENNGAY,112) >=  CONVERT(VARCHAR,GETDATE(),112)";
        Statement statement;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet getDanhSachTamVangHetHieuLuc() {
        ResultSet resultSet = null;
        String query= "SELECT * FROM TAMVANG TV , NHANKHAU NK WHERE TV.MANHANKHAU = NK.MANHANKHAU AND CONVERT(VARCHAR,DENNGAY,112) <  CONVERT(VARCHAR,GETDATE(),112)";
        Statement statement;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet lay_cac_thanh_vien(String ma_ho){
        String query = "select * from THANHVIENCUAHO where MAHOKHAU="+ma_ho;
        ResultSet resultSet=null;
        try{
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        }catch (Exception e){
            System.out.println("loi o truy van thanh vien");
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet lay_nhan_khau(String ma_nhan_khau) {
        String query = " select SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU from NHANKHAU where MANHANKHAU = " + ma_nhan_khau;
        return executeQuery(query);
    }

    public void add_thanh_vien_cua_ho(String maNhanKhau,String ma_ho, String quan_he){
        String query = "INSERT INTO THANHVIENCUAHO VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,maNhanKhau);
            preparedStatement.setString(2,ma_ho);
            preparedStatement.setNString(3,quan_he);
            preparedStatement.executeUpdate();

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public void xoa_thanh_vien_cua_ho(String maNhanKhau){
        String query1 = "select * FROM NHANKHAU WHERE MANHANKHAU = " + maNhanKhau;
        try{
            Statement statement1 = connection.createStatement();
            ResultSet resultSet=statement1.executeQuery(query1);
            if(resultSet.isBeforeFirst()) {
                resultSet.next();
                String query = "DELETE FROM THANHVIENCUAHO WHERE MANHANKHAU= "+resultSet.getString(1);
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
            }
        }catch (Exception e){
            System.out.println("loi o xoa_thanh_vien_cua_ho");
            e.printStackTrace();
        }
    }
    public void xoaThanhVienCuaHo(String maNhanKhau) {
        String query = "DELETE FROM THANHVIENCUAHO WHERE MANHANKHAU = " + maNhanKhau;
        System.out.println("da xoa " + maNhanKhau);
        executeUpdate(query);
    }

    public ResultSet truyvan_chua_co_nha() {
        ResultSet resultSet = null;
        String querry = " select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU from NHANKHAU WHERE MANHANKHAU NOT IN (SELECT MANHANKHAU FROM THANHVIENCUAHO);";
        try{
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(querry);
        }
        catch(Exception e) {
            System.out.println("loi truy van chua co ho khau");
        }
        return resultSet;
    }

    public ResultSet nhanKhau_timkiem_chua_co_nha(String string) {
        ResultSet resultSet = null;
        String querry = " select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU from NHANKHAU where (MANHANKHAU like ? or SOCANCUOC like ? or HOTEN like ?) AND MANHANKHAU NOT IN (SELECT MANHANKHAU FROM THANHVIENCUAHO);";
        try {
            PreparedStatement preparedstatement = connection.prepareStatement(querry);
            preparedstatement.setString(1, "%" + string + "%");
            preparedstatement.setString(2, "%" + string + "%");
            preparedstatement.setNString(3, "%" + string + "%");
            resultSet = preparedstatement.executeQuery();
        }
        catch(Exception e) {
            System.out.println("Lỗi tìm kiếm");
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet nhanKhau_timkiem_chua_co_nha_theo_manhankhau(String string) {
        ResultSet resultSet = null;
        String querry = " select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU from NHANKHAU where (MANHANKHAU like ?) AND MANHANKHAU NOT IN (SELECT MANHANKHAU FROM THANHVIENCUAHO);";
        try {
            PreparedStatement preparedstatement = connection.prepareStatement(querry);

            preparedstatement.setNString(1, "%" + string + "%");
            resultSet = preparedstatement.executeQuery();
        }
        catch(Exception e) {
            System.out.println("Lỗi tìm kiếm");
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet nhanKhau_timkiem_chua_co_nha_theo_soCCCD(String string) {
        ResultSet resultSet = null;
        String querry = " select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU from NHANKHAU where (SOCANCUOC like ?) AND MANHANKHAU NOT IN (SELECT MANHANKHAU FROM THANHVIENCUAHO);";
        try {
            PreparedStatement preparedstatement = connection.prepareStatement(querry);

            preparedstatement.setNString(1, "%" + string + "%");
            resultSet = preparedstatement.executeQuery();
        }
        catch(Exception e) {
            System.out.println("Lỗi tìm kiếm");
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet nhanKhau_timkiem_chua_co_nha_theo_HoTen(String string) {
        ResultSet resultSet = null;
        String querry = " select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU from NHANKHAU where (HOTEN like ?) AND MANHANKHAU NOT IN (SELECT MANHANKHAU FROM THANHVIENCUAHO);";
        try {
            PreparedStatement preparedstatement = connection.prepareStatement(querry);

            preparedstatement.setNString(1, "%" + string + "%");
            resultSet = preparedstatement.executeQuery();
        }
        catch(Exception e) {
            System.out.println("Lỗi tìm kiếm");
            throw new RuntimeException(e);
        }
        return resultSet;
    }

   public String lay_chu_ho(String ma_ho_khau){
        String query = "select * from HOKHAU WHERE MAHOKHAU="+ma_ho_khau;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery(query);

                if (resultSet.isBeforeFirst()) {
                    resultSet.next();
                    return resultSet.getString(2);
                }
        }catch (Exception e){
            System.out.println("loi o lay_ho_khau");
            return null;
        }
        return null;
   }



    public ResultSet getDSNguoiChet() {
        String query = "select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU\n" +
                "from NHANKHAU INNER JOIN KHAITU ON NHANKHAU.MANHANKHAU = KHAITU.MANHANKHAUNGUOICHET";

        return executeQuery(query);
    }

    public ResultSet deadNhanKhau_timkiem(String condition) {
        String query = "select MANHANKHAU, SOCANCUOC, HOTEN, GIOITINH, NGAYSINH, NOITHUONGTRU\n" +
                "from NHANKHAU INNER JOIN KHAITU ON NHANKHAU.MANHANKHAU = KHAITU.MANHANKHAUNGUOICHET\n" +
                "WHERE MANHANKHAU LIKE '%" + condition + "%' OR SOCANCUOC LIKE '%" + condition + "%' OR HOTEN LIKE N'%" + condition + "%'";

        return executeQuery(query);
    }
    public ResultSet getThongTinKhaiTu(String maNhanKhauNguoiChet) {
        String query = "SELECT KT.MAGIAYKHAITU, NK1.MANHANKHAU, NK1.HOTEN, NK2.MANHANKHAU, NK2.HOTEN, NK2.SOCANCUOC, NK2.NGAYSINH, NK2.GIOITINH, NK2.DANTOC, NK2.QUOCTICH,\n" +
                "\tNK2.NGUYENQUAN, NK2.NOITHUONGTRU, KT.NGAYKHAI, KT.NGAYCHET, KT.LYDOCHET\n" +
                "FROM KHAITU KT INNER JOIN NHANKHAU NK1 ON KT.MANHANKHAUNGUOIKHAI = NK1.MANHANKHAU\n" +
                "\tINNER JOIN NHANKHAU NK2 ON KT.MANHANKHAUNGUOICHET = NK2.MANHANKHAU\n" +
                "WHERE KT.MANHANKHAUNGUOICHET = " + maNhanKhauNguoiChet;
        return executeQuery(query);
    }

    public void updateThongTinKhaiTu(String maGiayKhaiTu, String ngayKhai, String ngayChet, String lyDo) {
        String query = "UPDATE KHAITU\n" +
                "SET NGAYKHAI = '" + ngayKhai + "', NGAYCHET = '" + ngayChet + "', LYDOCHET = N'" + lyDo + "'\n" +
                "WHERE MAGIAYKHAITU = " + maGiayKhaiTu;
        executeUpdate(query);
    }
    /***************************************************************************/


    public  void xoaTamVang(int magiaytamvang){
        String query = "DELETE TAMVANG  WHERE MAGIAYTAMVANG = " + magiaytamvang;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("deo xoa duoc");
        }
    }

    public  void xoaTamTru(int magiaytamtru){
        String query = "DELETE TAMTRU  WHERE MAGIAYTAMTRU = " + magiaytamtru;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("deo xoa duoc");
        }
    }

    public int xoaNhanKhau(String soNhanKhau) {
        String query = "DECLARE @OUTPUT INT\n" +
                "EXEC DELETE_NHANKHAU " + soNhanKhau + ", @OUTPUT OUTPUT\n" +
                "SELECT @OUTPUT";
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkKhaiTu(String maNhanKhau) {
        String query = "SELECT COUNT(MAGIAYKHAITU) FROM KHAITU WHERE MANHANKHAUNGUOICHET = " + maNhanKhau;
        ResultSet resultSet = executeQuery(query);
        try {
            resultSet.next();
            if (resultSet.getInt(1) == 0) return true; // chua chet
            else return false; // da chet
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkTamVang(String maNhanKhau) {
        String query = "SELECT COUNT(MAGIAYTAMVANG) FROM TAMVANG WHERE MANHANKHAU = " + maNhanKhau;
        ResultSet resultSet = executeQuery(query);
        try {
            resultSet.next();
            if (resultSet.getInt(1) == 0) return true; // chua di tam vang
            else return false;// da da di tam vang
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean checkTamTru(String maNhanKhau) {
        String query = "SELECT COUNT(MAGIAYTAMTRU) FROM TAMTRU WHERE MANHANKHAU =  " + maNhanKhau;
        ResultSet resultSet = executeQuery(query);
        try {
            resultSet.next();
            if (resultSet.getInt(1) == 0) return true; // dang trong tinh trang tam tru
            else return false; // khong tam tru
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}


