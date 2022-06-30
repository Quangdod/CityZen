use master
go

if exists(select * from sys.databases where name='CityZen')
	drop database CityZen
go

create database CityZen
go

use CityZen
go

create table NhaPhanPhoi(
	maNPP int identity(1,1) not null, -- indentity(gt bắt đầu, gt tăng)
	tenNPP nvarchar(40) not null,
	diaChi nvarchar(60) not null,
	sdt nchar(10) not null,
	email varchar(30) not null,
	ghiChu nvarchar(50)  null,
	loGo nvarchar(50) not null,
	primary key (maNPP)
)
go

create table Sach(
	maSach nvarchar(7) not null,
	tenSach nvarchar(40) not null,
	tacGia nvarchar(40) not null,
	soTrang smallint not null,
	diaChi nvarchar(60) not null,
	gia float not null,
	viTri nvarchar(40) not null,
	tenLoai nvarchar(40) not null,
	nxb nvarchar(30) not null,
	hinh nvarchar(50) not null,
	primary key (maSach)
	)
go
	
create table NhanVien(
	maNV nvarchar(7) not null,
	tenNV nvarchar(30) not null,
	gioiTinh nvarchar(10) not null,
	sdt nchar(10) not null,
	ngaySinh date not null,
	email varchar(30) not null,
	vaiTro bit not null,
	matKhau varchar(20) not null,
	diaChi nvarchar(50) not null,
	ghiChu nvarchar(50)  null,
	hinh nvarchar(50) not null,
	primary key (maNV)
)
go

create table KhachHang(
	maKH int identity(1,1) not null,
	tenKH nvarchar(30) not null, 
	gioiTinh nvarchar(10) not null, --int
	sdt char(10) not null,
	ngaySinh date not null,
	diaChi nvarchar(100) not null,
	ghiChu nvarchar(100) null,
	capKH tinyint not null,
	hinh varchar(50) not null
	primary key (maKH)
)
go

create table NhapSach(
	maLH int identity(1,1) not null,
	ngayNhap date not null,
	maNPP int not null,
	maNV nvarchar(7) not null,
	maSach nvarchar(7) not null,
	giaNhap float not null,
	soLuong smallint not null,
	ghiChu nvarchar(50) null,
	primary key (maLH),
	foreign key(maSach) references Sach(maSach) on delete cascade on update cascade,
	foreign key(maNV) references NhanVien(maNV) on delete no action on update cascade,
	foreign key(maNPP) references NhaPhanPhoi(maNPP) on delete no action on update cascade
)
go

create table HoaDon(
	maHD int identity(1,1) not null,
	ngayMua date not null,
	maNV nvarchar(7) not null,
	ghiChu nvarchar(50) null,
	maKH int not null,
	primary key (maHD),
	foreign key (maNV) references NhanVien(maNV)  on delete no action on update cascade,
	foreign key (maKH) references KhachHang(maKH) on delete no action on update cascade
)
go

create table HoaDonChiTiet(
	maHDCT int identity (1,1) not null,
	maSach nvarchar(7) not null,
	maHD int not null,
	soLuong tinyint not null,
	gia float not null,
	primary key (maHDCT),
	foreign key (maSach) references Sach(maSach) on delete cascade on update cascade,
	foreign key (maHD) references HoaDon(maHD) on delete no action on update cascade
)
go

insert NhaPhanPhoi (tenNPP,diaChi,sdt,email,ghiChu,loGo)
		values (N'Nhà Xuất Bản Thuận Hóa Huế',N'33 Chu Văn An,Phường Phú Hội,Thừa Thiên Huế', 0847774910,'phanlehanh87@gmail.com',null,N'NXBThuanHoaHue.PNG'),
			(N'Công Ty TNHH Văn Hóa Việt Long',N'14/35,Đào Duy Anh,P.9,Q.Phú Nhuận,Hồ Chí Minh',02838452708,'info@vietlonbook.com',null,N'ctyvietlong.png'),
			(N'Nhà Sách Trực Tuyến BOOKBUY',N'147 Pasteur, P.6, Q.3,Tp.Hồ Chí Minh (TPHCM)',0933109009,'nfo@bookbuy.vn',null,N'bookbuy.png'),
			(N'Công Ty TNHH Đăng Nguyên',N'Thôn Đức Mỹ, X. Suối Nghệ, H. Châu Đức, Bà Rịa-Vũng Tàu',02543716857,'vudinh200574@yahoo.com',null,N'dangnguyen.png'),
			(N'Công Ty  Sách Mcbooks',N'Khu Đấu Giá 3ha, P.Phúc Diễm, Q.Bắc Từ Liêm, Hà Nội',0986066630,'thongtinsach@mcbooks.vn',null,N'mcbook.jpg'),
			(N'Công Ty Cổ Phần  Hà Nội',N' 48 Lê Văn Lương, P. Nhân Chính, Q.Thanh Xuân Hà Nội',02435121977,'info@xbgdhn.vn',null,N'xbgdHaNoi.png')

	
insert 	Sach	(maSach, tenSach, tacGia, soTrang, diaChi, gia, viTri,tenLoai,nxb,hinh)
		values(N'LT00002',N'Lập Trình Java Nâng Cao',N'Đoàn Văn Ban',280,N'Quận 3,TP Hồ Chí Minh',180000,N'Dãy 1, hàng 3, ngăn 2',N'Lập Trình',N'NXB Khoa Học và Kĩ thuật',N'javanangcao.png'),
		(N'LT00003',N'Lập Trình C',N'Phạm Văn Ất',180,N'Quận 10,TP Hồ Chí Minh',170000,N'Dãy 1, hàng 4, ngăn 5',N'Lập Trình',N'NXB Bách Khoa Hà Nội',N'laptrinhC.jpg'),
		(N'LT00004',N'Lập Trình C++',N'Phạm Văn Ất',150,N'Quận 10,TP Hồ Chí Minh',150000,N'Dãy 1, hàng 5, ngăn 5',N'Lập Trình',N'NXB Bách Khoa Hà Nội',N'laptrinhC++.jpg'),
		(N'LT00001',N'Lập Trình Java',N'Thomat Ine',200,N'Quận 1,TP Hồ Chí Minh',150000,N'Dãy 1, hàng 2, ngăn 6',N'Lập Trình',N'topCV',N'laptrinhJava.png'),
		(N'GT00001',N'Tinh thông toán học',N'LILIAN YEO',100,N'Minh Long Book,Q.Hoàn Kiếm, Hà NỘi',63000,N'Dãy 1, hàng 2, ngăn 3',N'Giáo Trình',N'ĐH Quốc Gia Hà Nội',N'GTtoanhoc.PNG'),
		(N'GT00002',N'Giáo Trình Pháp Luật',N'Phan Tuyết Ngọc',90,N'Minh Long Book,Q.Hoàn Kiếm, Hà NỘi',85000,N'Dãy 1, hàng 3, ngăn 4',N'Giáo Trình',N'ĐH Quốc Gia Hà Nội',N'GTphapluat.jpg'),
		(N'GT00003',N'Giáo Trình Anh Văn',N'Phạm Thái Hà',90,N'Minh Long Book,Q.Hoàn Kiếm, Hà NỘi',70000,N'Dãy 1, hàng 4, ngăn 4',N'Giáo Trình',N'ĐH Quốc Gia Hà Nội',N'GTanhvan.jpg'),
		(N'KT00001',N'Quốc gia khởi nghiệp',N'Dan Senor',320,N'Quận 5,TP Hồ Chí Minh',200000,N'Dãy 1, hàng 2, ngăn 7',N'Kinh Tế',N'ĐH Quốc Gia Hà Nội',N'QGKN.PNG'),
		(N'KT00002',N'Cách nền kinh tế vận hành',N'Farmer',360,N'Quận 5,TP Hồ Chí Minh',240000,N'Dãy 1, hàng 5, ngăn 7',N'Kinh Tế',N'NXB Tri Thức',N'sachKinhte.jpg'),
		(N'TC00001',N'Kiếp sau',N'Marc Levy',140,N'Quận 7,TP Hồ Chí Minh',140000,N'Dãy 1, hàng 2, ngăn 8',N'Tiểu Thuyết ',N'Nhà Xuất Bản Hội Nhà Văn',N'kiepsau.jpg'),
		(N'TC00002',N'Nhà Giả Kim',N'Paulo Coelho',150,N'Quận 7,TP Hồ Chí Minh',180000,N'Dãy 1, hàng 4, ngăn 8',N'Tiểu Thuyết ',N'NXB văn học',N'nhagia.jpg'),
		(N'TA00001',N'Từ Vựng Luyện Thi Ielts',N'Joih sơn',150,N'Quận 8,TP Hồ Chí Minh',100000,N'Dãy 1, hàng 1, ngăn 2',N'Tiếng Anh',N'ĐH Quốc Gia Hà Nội',N'QGKN.PNG'),
		(N'LS00001',N'Đại Việt Sử Ký Toàn Thư Trọn Bộ',N'Đào Duy Anh',1280,N'Quận 2,TP Hồ Chí Minh',288000,N'Dãy 1, hàng 1, ngăn 1',N'Lịch Sử',N'Nhà Xuất Bản Hồng Đức',N'daivietsuki.jpg'),
		(N'YH00001',N'Trung Y Học Khái Luận',N'Hoàng Thái',428,N'Quận 5,TP Hồ Chí Minh',239000,N'Dãy 1, hàng 1, ngăn 3',N'Y Học',N'Nhà Xuất Bản Hồng Đức',N'trungyhockhailuan.jpg')
	
insert NhanVien (maNV, tenNV,gioiTinh,sdt,ngaySinh,email,vaiTro,matKhau,diaChi,ghiChu,hinh)
		values(N'CTZ0001',N'Nguyễn Hoàng Lãng',N'Nam',0965583425,'2002/02/12',N'langpopo@gmail.com',1,N'123456',N'CV phần mềm Quang Trung,Quận 12',null,N'lang.jpg'),
			(N'CTZ0002',N'Nguyễn Đạt Văn',N'Nam',0947563425,'1998/08/12',N'datVan@gmail.com',1,N'vd3456',N'CV phần mềm Quang Trung,Quận 12',null,N'Van.jpg'),
			(N'Quang',N'Đại Quang',N'Nam',094721254,'1998/08/12',N'quang@gmail.com',1,N'123',N'CV phần mềm Quang Trung,Quận 12',null,N'Van.jpg'),
			(N'CTZ0003',N'Nguyễn Thị Lan',N'Nữ',0988883425,'1999/03/12',N'lanpopo@gmail.com',1,N'tl3456',N'Quận 11,Thành phố HCM',null,N'lan.jpg')



insert KhachHang(tenKH,gioiTinh, sdt,ngaySinh,diaChi,ghiChu,capKH,hinh)
		values(N'Trần Thanh Tùng',N'Nam',0987654321,'2002/03/14',N'Hoàng Văn Thụ,quận 1,HCM',null,0,'tung.png'),
		(N'Nguyễn Hoàng Mai',N'Nữ',0987675121,'2000/08/21',N'quận Gò Vấp,HCM',null,0,'mai.jpg'),
		(N'Lê Mai Loan',N'Nữ',0987673891,'1996/08/21',N'quận 11,HCM',null,0,'Loan.jfif'),
		(N'như anh',N'Khác',0987673891,'1996/08/21',N'quận 11,HCM',null,0,'Loan.jfif')

insert NhapSach(ngayNhap,maNPP,maNV,maSach,giaNhap,soLuong,ghiChu)
		values('2020/03/12',1,N'CTZ0001','LT00001',120000,50,null),
			('2020/07/12',2,N'CTZ0002','KT00001',170000,30,null),
			('2020/11/12',3,N'CTZ0003','GT00001',50000,50,null)

insert HoaDon(ngayMua,maNV,ghiChu,maKH)
		values('2020/02/25',N'CTZ0001',null,1),
			('2019/03/25',N'CTZ0002',null,3),
			('2020/12/25',N'CTZ0001',null,2)

insert HoaDonChiTiet(maSach,maHD,soLuong,gia)
		values(N'LT00001',1,2,300000),
			(N'KT00001',1,1,200000),
			(N'GT00001',1,6,360000),
			(N'LT00001',2,1,140000),
			(N'GT00001',2,1,60000),
			(N'KT00001',3,3,600000),
			(N'GT00001',3,30,18000000)

--////////////////////////////////////////////////////////////////////////////

INSERT INTO NhapSach (ngayNhap, maNPP, maNV, maSach, giaNhap, soLuong, ghiChu) VALUES ('2002-02-01', '3', 'Quang', 'LT00001', 50, 50, 'fttrfdrt');

UPDATE NhapSach SET ngayNhap='2002-02-01', maNPP=3, maNV='Quang', maSach='LT00001', giaNhap=600, soLuong=50, ghiChu='fttrfdrt' WHERE maLH=5

select * from NhapSach