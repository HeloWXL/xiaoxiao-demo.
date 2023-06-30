package xx.rabbitmq.chat.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xx.rabbitmq.chat.constant.ConstantUtils;

@Configuration
public class FanoutRabbitConfig {

    /**
     *  创建三个队列 ：fanout.msg
     *  将三个队列都绑定在交换机 fanoutExchange 上
     *  因为是扇型交换机, 路由键无需配置,配置也不起作用
     */
    @Bean
    public Queue queueMsg() {
        return new Queue(ConstantUtils.FANOUT_QUEUE_MSG);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(ConstantUtils.FANOUT_EXCHANGE);
    }

    @Bean
    Binding bindingExchangeA() {
        return BindingBuilder.bind(queueMsg()).to(fanoutExchange());
    }
}
