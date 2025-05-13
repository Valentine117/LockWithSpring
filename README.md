# 🔐 Spring Lock Demo

이 프로젝트는 **Spring Boot + JPA** 기반으로 동시성 문제와 락(Lock)의 원리를 실습하기 위한 데모 프로젝트입니다.  
블로그 시리즈 "[락의 기초와 종류 – Spring + DB로 개념 잡기]([#](https://velog.io/@valentine117/%EB%9D%BDLock%EC%9D%98-%EA%B8%B0%EC%B4%88%EC%99%80-%EC%A2%85%EB%A5%98-1%EC%9E%A5-Spring-DB%EC%84%A4%EC%A0%95%EA%B3%BC-%EB%B9%84%EA%B4%80%EC%A0%81-%EB%9D%BD-%ED%85%8C%EC%8A%A4%ED%8A%B8))" 와 함께 보시면 더욱 이해가 잘 됩니다.

---

## 📁 프로젝트 개요

- Java 17
- Spring Boot 3.4.5
- H2 In-Memory Database
- Spring Data JPA
- 비관적 락(Pessimistic Lock) 실습

---

## 🧱 도메인 구조

### Stock

```java
@Entity
public class Stock {
    @Id
    private Long id;
    private Integer quantity;

    public void decrease(int amount) {
        if (this.quantity - amount < 0) throw new IllegalStateException("재고 부족");
        this.quantity -= amount;
    }
}
```

---

## 📦 주요 클래스

| 클래스 | 설명 |
|--------|------|
| `StockRepository` | JPA 기반 Repository, `@Lock(PESSIMISTIC_WRITE)` 사용 |
| `PlainStockService` | 락 없이 재고 차감 (문제 유발용) |
| `PessimisticLockStockService` | 비관적 락으로 안전하게 재고 차감 |
| `StockController` | API 테스트용 컨트롤러 |
| `StockConcurrencyTest` | 멀티 쓰레드 테스트로 동시성 문제 확인 |

---

## 🚀 실행 방법

1. 프로젝트 클론

```bash
git clone https://github.com/your-username/spring-lock-demo.git
cd spring-lock-demo
```

2. Gradle 빌드

```bash
./gradlew build
```

3. 서버 실행

```bash
./gradlew bootRun
```

---

## ✅ API 테스트 예시

- 락 없이 감소:  
  `POST /stock/plain?id=1&quantity=10`

- 락 적용해서 감소:  
  `POST /stock/pessimistic?id=1&quantity=10`

---

## 🧪 테스트 실행

```bash
./gradlew test
```

테스트 클래스 `StockConcurrencyTest`를 통해 락 유무에 따른 결과 차이를 확인할 수 있습니다.

---

## 📝 관련 블로그

- [1편: 락의 기초와 종류 – Spring + DB로 개념 잡기](https://velog.io/@valentine117/%EB%9D%BDLock%EC%9D%98-%EA%B8%B0%EC%B4%88%EC%99%80-%EC%A2%85%EB%A5%98-1%EC%9E%A5-Spring-DB%EC%84%A4%EC%A0%95%EA%B3%BC-%EB%B9%84%EA%B4%80%EC%A0%81-%EB%9D%BD-%ED%85%8C%EC%8A%A4%ED%8A%B8)

---

## 📄 라이선스

MIT License
