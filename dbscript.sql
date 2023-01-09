CREATE TABLE `AuthRefreshToken` (
  `userId` varchar(50) NOT NULL COMMENT '관리자 ID',
  `refreshToken` varchar(256) NOT NULL COMMENT '리프레쉬 토큰',
  `regDate` datetime NOT NULL COMMENT '등록일시',
  `regId` varchar(50) NOT NULL COMMENT '등록자',
  `updDate` datetime DEFAULT NULL COMMENT '수정일시',
  `updId` varchar(50) DEFAULT NULL COMMENT '수정자',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='리프레쉬 토큰 관리';

CREATE TABLE `User` (
  `userSeq` int NOT NULL AUTO_INCREMENT COMMENT '사용자 일련번호',
  `userId` varchar(50) NOT NULL COMMENT '사용자 ID',
  `userPwd` varchar(100) NOT NULL COMMENT '사용자 비밀번호',
  `userName` varchar(100) NOT NULL COMMENT '사용자명',
  `phoneNo` varchar(20) DEFAULT NULL COMMENT '전화번호',
  `email` varchar(20) DEFAULT NULL COMMENT '이메일 주소',
  `lastLoginDate` datetime DEFAULT NULL COMMENT '마지막 로그인 일시',
  `lastLoginIP` varchar(24) DEFAULT NULL COMMENT '마지막 로그인 아이피',
  `useFlag` bit(1) NOT NULL DEFAULT b'1' COMMENT '사용 여부',
  `regDate` datetime NOT NULL COMMENT '등록일시',
  `regId` varchar(50) NOT NULL COMMENT '등록자',
  `updDate` datetime DEFAULT NULL COMMENT '수정일시',
  `updId` varchar(50) DEFAULT NULL COMMENT '수정자',
  PRIMARY KEY (`userSeq`),
  UNIQUE KEY `User_UserId_IDX` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='사용자 정보';



CREATE TABLE `Delivery` (
  `seq` bigint NOT NULL AUTO_INCREMENT COMMENT '배달 일련번호',
  `requestDate` datetime NOT NULL COMMENT '배달 요청일시',
  `destAddress` varchar(100) NOT NULL COMMENT '배달주소',
  `state` enum('READY','IN_DELIVERY','COMPLETE') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '배달 상태',
  `completeDate` datetime DEFAULT NULL COMMENT '배달 완료일시',
  `totalAmount` int NOT NULL COMMENT '배달 상품 총 금액',
  `fees` int NOT NULL COMMENT '배달 수수료',
  `regDate` datetime NOT NULL COMMENT '등록일시',
  `regId` varchar(50) NOT NULL COMMENT '등록자',
  `updDate` datetime DEFAULT NULL COMMENT '수정일시',
  `updId` varchar(50) DEFAULT NULL COMMENT '수정자',
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='배달 정보';

CREATE TABLE `Product` (
  `seq` bigint NOT NULL AUTO_INCREMENT COMMENT '상품 일련번호',
  `name` varchar(100) NOT NULL COMMENT '상품명',
  `amount` int NOT NULL COMMENT '상품 가격',
  `regDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `regId` varchar(50) NOT NULL COMMENT '등록자',
  `updDate` datetime DEFAULT NULL COMMENT '수정일시',
  `updId` varchar(50) DEFAULT NULL COMMENT '수정자',
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='상품 정보';

CREATE TABLE `DeliveryProduct` (
  `deliverySeq` bigint NOT NULL COMMENT '배달 일련번호',
  `productSeq` bigint NOT NULL COMMENT '상품 일련번호',
  `count` int NOT NULL COMMENT '주문 개수',
  `amount` int NOT NULL COMMENT '상품 금액',
  `regDate` datetime NOT NULL COMMENT '등록일시',
  `regId` varchar(50) NOT NULL COMMENT '등록자',
  `updDate` datetime DEFAULT NULL COMMENT '수정일시',
  `updId` varchar(50) DEFAULT NULL COMMENT '수정자',
  PRIMARY KEY (`deliverySeq`,`productSeq`),
  KEY `DeliveryProduct_FK_1` (`productSeq`),
  CONSTRAINT `DeliveryProduct_FK` FOREIGN KEY (`deliverySeq`) REFERENCES `Delivery` (`seq`),
  CONSTRAINT `DeliveryProduct_FK_1` FOREIGN KEY (`productSeq`) REFERENCES `Product` (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='배달 상품 정보';