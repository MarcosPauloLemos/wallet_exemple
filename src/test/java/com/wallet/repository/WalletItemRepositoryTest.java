package com.wallet.repository;

import com.wallet.model.Wallet;
import com.wallet.model.WalletItem;
import com.wallet.model.enums.TypeEnum;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class WalletItemRepositoryTest {

    private static final Date DATE = new Date();
    private static final TypeEnum TYPE = TypeEnum.EN;
    private static final String DESCRIPTION = "Conta de luz";
    private static final BigDecimal VALUE = BigDecimal.valueOf(65);
    private Long savedWalletId = null;
    private Long savedWalletItemId = null;

    @Autowired
    private WalletItemRepository repository;

    @Autowired
    private WalletRepository walletRepository;

    @Before
    public void setUp(){
        Wallet wallet = Wallet.builder()
                .name("Carteira teste")
                .value(BigDecimal.valueOf(250))
                .build();
        walletRepository.save(wallet);
        savedWalletId = wallet.getId();

        WalletItem walletItem = new WalletItem(null,wallet, DATE, TYPE, DESCRIPTION, VALUE);
        repository.save(walletItem);
        savedWalletItemId = walletItem.getId();
    }

    @After
    public  void tearDown(){
        repository.deleteAll();
        walletRepository.deleteAll();
    }

    @Test
    public void testSave(){
        Wallet wallet = Wallet.builder()
                .id(null)
                .name("Carteira 1")
                .value(BigDecimal.valueOf(500))
                .build();
        walletRepository.save(wallet);

        WalletItem walletItem = new WalletItem(null,wallet, DATE, TYPE, DESCRIPTION, VALUE);
        WalletItem walletItemResponse = repository.save(walletItem);
        Assert.assertNotNull(walletItemResponse);
        Assert.assertEquals(walletItemResponse.getDescription(), DESCRIPTION);
        Assert.assertEquals(walletItemResponse.getType(), TYPE);
        Assert.assertEquals(walletItemResponse.getValue(), VALUE);
        Assert.assertEquals(walletItemResponse.getWallet().getId(), wallet.getId());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testSaveInvalidWalletItem() {
        WalletItem walletItem = new WalletItem(null,null,DATE,null,DESCRIPTION,null);
        repository.save(walletItem);
    }

    @Test
    public void testUpdate() {
        Optional<WalletItem> oWalletItem = repository.findById(savedWalletItemId);
        String description = "Descrição alterada";
        WalletItem changedWalletItem = oWalletItem.get();
        changedWalletItem.setDescription(description);
        repository.save(changedWalletItem);
        Optional<WalletItem> oNewWalletItem = repository.findById(savedWalletItemId);
        Assert.assertEquals(description,oNewWalletItem.get().getDescription());
    }

    @Test
    public void deleteWalletItem() {
        Optional<Wallet> oWallet = walletRepository.findById(savedWalletId);
        WalletItem walletItem = new WalletItem(null, oWallet.get(), DATE, TYPE, DESCRIPTION, VALUE);
        repository.save(walletItem);
        repository.deleteById(walletItem.getId());
        Optional<WalletItem> oWalletItem = repository.findById(walletItem.getId());
        Assert.assertFalse(oWalletItem.isPresent());
    }

    @Test
    public void testFindBetweenDates(){
        Optional<Wallet> wallet = walletRepository.findById(savedWalletId);
        LocalDateTime localDateTime = LocalDateTime.now();

        Date currentDatePlusFiveDays = Date.from(localDateTime.plusDays(5).atZone(ZoneId.systemDefault()).toInstant());
        Date currentDatePlusSevenDays = Date.from(localDateTime.plusDays(7).atZone(ZoneId.systemDefault()).toInstant());

        repository.save(new WalletItem(null, wallet.get(), currentDatePlusFiveDays, TYPE, DESCRIPTION, VALUE));
        repository.save(new WalletItem(null, wallet.get(), currentDatePlusSevenDays, TYPE, DESCRIPTION, VALUE));

        PageRequest pg = PageRequest.of(0,10);
        Page<WalletItem> response = repository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual
                (savedWalletId, DATE, currentDatePlusFiveDays, pg);

        Assert.assertEquals(response.getContent().size(), 2);
        Assert.assertEquals(response.getTotalElements(),2);
        Assert.assertEquals(response.getContent().get(0).getWallet().getId(),savedWalletId);
    }

    @Test
    public void testFindByType(){
        List<WalletItem> reponse = repository.findByWalletIdAndType(savedWalletId, TYPE);
        Assert.assertEquals(reponse.size(), 1);
        Assert.assertEquals(reponse.get(0).getType(), TYPE);
    }

    @Test
    public void testFindByTypeSd() {
        Optional<Wallet> wallet = walletRepository.findById(savedWalletId);
        repository.save(new WalletItem(null,wallet.get(),DATE,TypeEnum.SD,DESCRIPTION,VALUE));
        List<WalletItem> reponse = repository.findByType(TypeEnum.SD);
        Assert.assertEquals(reponse.size(), 1);
        Assert.assertEquals(reponse.get(0).getType(), TypeEnum.SD);
    }

    @Test
    public void testSumByWallet(){
        Optional<Wallet> oWallet = walletRepository.findById(savedWalletId);
        repository.save(new WalletItem(null, oWallet.get(), DATE, TYPE, DESCRIPTION, BigDecimal.valueOf(150.80)));
        BigDecimal response = repository.sumValueByWalletId(savedWalletId);
        Assert.assertEquals(response.compareTo(BigDecimal.valueOf(215.8)),0);
    }
}
