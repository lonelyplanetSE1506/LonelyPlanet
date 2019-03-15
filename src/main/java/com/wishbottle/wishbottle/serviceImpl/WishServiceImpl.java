//心愿管理服务类
package com.wishbottle.wishbottle.serviceImpl;
import com.wishbottle.wishbottle.bean.Wish;
import com.wishbottle.wishbottle.repository.WishRepository;
import com.wishbottle.wishbottle.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WishServiceImpl implements WishService {
    @Autowired
    private WishRepository WishRepository;
    //查询全部心愿
    @Override
    public List<Wish> getAllWish() { return WishRepository.findAll(); }
    //按照WishID进行查询
    @Override
    public Optional<Wish> findByID(Integer id) {
        return WishRepository.findById(id);
    }
    //删除心愿
    @Override
    public void deleteWish(Wish wish) {
        WishRepository.delete(wish);
    }
    //删除心愿
    @Override
    public void deleteWish(Integer id) {
        WishRepository.deleteById(id);
    }

    //模糊查询
    //根据心愿发布者的用户名、心愿标题、心愿内容查询心愿
    @Override
    public List<Wish> search(String search) {
        return  WishRepository.queryBySearch(search);
    }
    //根据心愿id WishID查询心愿
    @Override
    public List<Wish> search(Integer search) {
        return  WishRepository.queryBySearch(search);
    }
    //添加心愿
    @Override
    public Wish addWish(Wish wish){
        return WishRepository.save(wish);
    }
    //根据可见性进行查找（可见性：仅自己可见、公开）
    @Override
    public List<Wish> getByPermision(boolean permisiom) {
        return WishRepository.queryByPermision(permisiom);
    }
    //根据心愿发布者的账号id　　AccountID进行查询
    @Override
    public List<Wish> getByAccountID(Integer accountID) {
        return WishRepository.queryByAccountID(accountID);
    }
    //更新心愿,修改心愿
    @Override
    public Wish updateWish(Wish awish) {
        return WishRepository.save(awish);
    }
    //获取点赞前十的wish
    @Override
    public List<Wish> getTop10(){
        List<Wish> wishes=WishRepository.queryOrderByGoodNum();
        int num=0;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String date=(df.format(new Date()));
        List<Wish> re=new ArrayList<>();
        for(Wish wish:wishes)
            if(num<10)
                if((df.format(wish.getRelTime())).equals(date))
                {num++;re.add(wish);}
        return re;
    }
    //随机获取10个心愿
    @Override
    public List<Wish> getRan10(){
        List<Wish> all=WishRepository.findAll();
        List<Wish> re=new ArrayList<>();
        int[] num=new int[10];//记录已经获取的数字
        int ran=0,length=all.size();
        Random r=new Random();
        if(length==0)
            return null;
        if(length<=10)
            return all;
        while(ran<10){
            num[ran]=r.nextInt(length);
            int i=0;
            //排除重复随机数
            while(i<ran&&num[i]!=num[ran])
                i++;
            if(i==ran) {
                re.add(all.get(num[ran]));
               //System.out.println(num[ran]);
                ran++;
            }
        }
        return re;
    }

}