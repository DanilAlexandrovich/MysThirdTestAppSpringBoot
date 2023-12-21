package ru.dubovitskiy.MysSecondTestAppSpringBoot.model;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    //Уникальный идентификатор пользователя
    @Size(max = 32, message = "uid должен содержать не более 32 символов")
    private String uid;
    //Уникальный идентификатор операции
    @Size(max = 32, message = "operationUid должен содержать не более 32 символов")
    private String operationUid;
    //Название системы
    private Systems systemName;
    //Время в системе
    @NotBlank(message = "systemTime не может быть пустым")
    private String systemTime;
    //Источник запроса
    private String source;
    //Идентификатор коммуникации
    @Min(value = 1, message = "communicationId должен быть больше или равен 1")
    @Max(value = 100000, message = "communicationId должен быть меньше или равен 100000")
    private int communicationId;

    //Идентификатор шаблона
    private int templateId;
    //Код продукта
    private int productCode;
    //SMS код
    private int smsCode;

    @Override
    public String toString() {
        return "{" +
                "uid='" + uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", systemName='" + systemName + '\'' +
                ", systemTime='" + systemTime + '\'' +
                ", source='" + source + '\'' +
                ", communicationId=" + communicationId +
                ", templateId=" + templateId +
                ", productCode=" + productCode +
                ", smsCode=" + smsCode +
                '}';
        }
    }
