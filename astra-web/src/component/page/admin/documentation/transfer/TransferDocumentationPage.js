import withTitle from "../../../../hoc/withTitle/withTitle";
import InfoHeader from "../../../../InfoHeader/InfoHeader";
import Table from "../../../../Table/Table";
import Alert from "../../../../Alert/Alert";
import "./TransferDocumentationPage.css";
import Spacer from "../../../../Spacer/Spacer";
import testingUkrTestbase from "./images/testingUkrTestbase.jpg";
import testingUkrTestbaseLink from "./images/testingUkrTestbaseLink.jpg";
import webImport from "./images/webImport.jpg";
import exportForm from "./images/exportForm.jpg";

const TransferDocumentationPage = () => {
    return (
        <div className="TransferDocumentationPage container">
            <InfoHeader>Імпорт</InfoHeader>
            <div className="chapter">
                <div className="content">
                    AstraKROK надає можливість імпорту тестів. При імпорті, тест перевіряється на правильність заповнення полів:
                    <ul className="browser-default">
                        <li>Поле "Питання" містить щонайменше 10 символів</li>
                        <li>Поле "Коментар" містить щонайменше 10 символів</li>
                        <li>Поле "Варіант" містить щонайменше 1 символів</li>
                        <li>Поле "Пояснення" містить щонайменше 10 символів</li>
                        <li>Тест містить щонайменше один варіант відповіді</li>
                        <li>Тест містить щонайменше один предмет</li>
                    </ul>
                    <div>Якщо хоча б одна з цих умов не виконується, то тест буде перенесений в чернетки.</div>
                    <div>На даний момент AstraKROK дозволяє імпортувати тести з файлу та веб-ресурсу.</div>
                </div>
                <Spacer height={10} />
                <div className="header">Імпорт з файлу</div>
                <div className="content">
                    Astra підтримує імпорт тестів з xls/xlsx та csv файлів. Для цього потрібно завантажити файл, з таблицею. У випадку Excel файлу, таблиця повинна мати наступний формат:
                    <Table type="secondary" className="disabled">
                        <thead>
                            <tr>
                                <th>№</th>
                                <th>Питання</th>
                                <th>Коментар</th>
                                <th>Предмет</th>
                                <th>Варіант</th>
                                <th>Пояснення</th>
                                <th>+/-</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td rowSpan={5}>1</td>
                                <td rowSpan={5}>Паренхіма аденогіпофізу представлена трабекулами, утвореними залозистими клітинами. Серед аденоцитів є клітини з гранулами, які забарвлюються основними барвниками і містять глікопротеїди. Які це клітини?</td>
                                <td rowSpan={5}>Аденогіпофіз – частина гіпофізу (Hypophysis, glandula pituitaria) – центрального ендокринного органа, функція якого полягає у регуляції діяльності периферійних частин ендокринної системи. Гіпофіз складається з чотирьох часток: дистальної (передньої), проміжної (середньої), туберальної та задньої...</td>
                                <td rowSpan={5}>Гістологія, Біологія|Загальна лікарська підготовка</td>
                                <td>Гонадотропоцити, тиротропоцити</td>
                                <td>Базофільні клітини містять у своєму складі гранули, що інтенсивно зв’язують основні барвники, тобто добре фарбуються основними барвниками. Знаходяться у передній частці гіпофіза...</td>
                                <td>+</td>
                            </tr>
                            <tr>
                                <td>Меланотропоцити</td>
                                <td>​Базофільні клітини проміжної частки гіпофіза, секретують мелатонін (меланотропний гормон), що впливає на пігментний обмін. Однак, меланотропін, що міститься та декретується меланотропоцтами, за своєю хімічною природою є пептидом.</td>
                                <td>-</td>
                            </tr>
                            <tr>
                                <td>Соматотропоцити</td>
                                <td>Ацидофільні клітини неправильної округлої форми.Соматотропоцити виробляють соматотропін – СоматоТропний Гормон – СТГ – гормон, що регулює процеси росту організму та його частин.</td>
                                <td>-</td>
                            </tr>
                            <tr>
                                <td>Мамотропоцити</td>
                                <td>Ацидофільні клітини неправильної круглої форми, що продукують лактотропін – ЛактоТропний Гормон ЛТГ, що стимулює діяльність молочних залоз, активує вироблення молока, посилює функцію жовтого тіла в яєчниках та впливає на статевий розвиток.</td>
                                <td>-</td>
                            </tr>
                            <tr>
                                <td>Хромофобні клітини</td>
                                <td>Хромофорні становлять 60% клітинної маси дистальної частки гіпофіза. Ці клітини слабо фарбуються гістологічними барвниками, оскільки у цитоплазмі відсутні гранули...</td>
                                <td>-</td>
                            </tr>

                            <tr>
                                <td rowSpan={5}>2</td>
                                <td rowSpan={5}>Захворювання бері-бері - це класична форма недостатності вітаміну тіаміну. Активна форма його синтезується за допомогою ферменту з класу:</td>
                                <td rowSpan={5}>Тіамін, Вітамін В1, антиневритний.У складі тіаміну наявне піримідинове кільце, з’єднане з тіазольним кільцем. Всмоктується у тонкому кишківнику у вигляді вільного тіаміну. Вітамін безпосередньо фосфорилюється у клітині-мішені. Приблизно 50% тіаміну знаходиться у м’язах, приблизно 40% у печінці.</td>
                                <td rowSpan={5}>Біологічна хімія, Фармакологія</td>
                                <td>Трансфераз</td>
                                <td>Траснферази - ферменти, що каналізують перенесення хімічних груп з однієї молекули субстрату на іншу.</td>
                                <td>+</td>
                            </tr>
                            <tr>
                                <td>Оксидоредуктаз</td>
                                <td>Оксидоредуктази -ферменти, що каналізують окисно-відновні реакції. Утворення ТДФ – коферментної (активної) форми вітаміну В1 каталізується тіамінкіназою, ферменту з класу трансфераз.</td>
                                <td>-</td>
                            </tr>
                            <tr>
                                <td>Гідролаз</td>
                                <td>Гідролази – ферменти що каналізують гідроліз хімічних зв’язків. Під час реакції утворення ТДФ - коферментної (активної) форми вітаміну В1 відбувається перенесення фосфатної групи з молекули АТФ на молекулу тіаміну.</td>
                                <td>-</td>
                            </tr>
                            <tr>
                                <td>Ліаз</td>
                                <td>Ліази - ферменти, що каналізують розрив хімічних зв’язків без гідролізу, з утворенням подвійного зв’язку в одному з продуктів.</td>
                                <td>-</td>
                            </tr>
                            <tr>
                                <td>Ізомераз</td>
                                <td>Ізомерази - ферменти, що каналізують структурні або геометричні зміни субстрату. Утврення ТДФ – реакція, що супроводжується перенесенням фосфатної групи з молекули АТФ та молекулу тіаміну. Ізомеризація не відбувається.</td>
                                <td>-</td>
                            </tr>
                        </tbody>
                    </Table>
                    Для таблиці у форматі csv, вигляд повинен бути наступним:
                    <Table type="secondary" className="disabled">
                        <thead>
                            <tr>
                                <th>№</th>
                                <th>Питання</th>
                                <th>Коментар</th>
                                <th>Предмет</th>
                                <th>Варіант</th>
                                <th>Пояснення</th>
                                <th>+/-</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1</td>
                                <td>Паренхіма аденогіпофізу представлена трабекулами...</td>
                                <td>Аденогіпофіз – частина гіпофізу (Hypophysis, glandula pituitaria) – центрального ендокринного...</td>
                                <td>Гістологія, Біологія|Загальна лікарська підготовка</td>
                                <td>Гонадотропоцити, тиротропоцити</td>
                                <td>Базофільні клітини містять у своєму складі гранули, що...</td>
                                <td>+</td>
                            </tr>
                            <tr>
                                <td>1</td>
                                <td>Паренхіма аденогіпофізу представлена трабекулами...</td>
                                <td>Аденогіпофіз – частина гіпофізу (Hypophysis, glandula pituitaria) – центрального ендокринного...</td>
                                <td>Гістологія, Біологія|Загальна лікарська підготовка</td>
                                <td>Меланотропоцити</td>
                                <td>​Базофільні клітини проміжної частки гіпофіза, секретують...</td>
                                <td>-</td>
                            </tr>
                            <tr>
                                <td>1</td>
                                <td>Паренхіма аденогіпофізу представлена трабекулами...</td>
                                <td>Аденогіпофіз – частина гіпофізу (Hypophysis, glandula pituitaria) – центрального ендокринного...</td>
                                <td>Гістологія, Біологія|Загальна лікарська підготовка</td>
                                <td>Соматотропоцити</td>
                                <td>Ацидофільні клітини неправильної округлої форми...</td>
                                <td>-</td>
                            </tr>
                            <tr>
                                <td>1</td>
                                <td>Паренхіма аденогіпофізу представлена трабекулами...</td>
                                <td>Аденогіпофіз – частина гіпофізу (Hypophysis, glandula pituitaria) – центрального ендокринного...</td>
                                <td>Гістологія, Біологія|Загальна лікарська підготовка</td>
                                <td>Мамотропоцити</td>
                                <td>Ацидофільні клітини неправильної круглої форми, що продукують...</td>
                                <td>-</td>
                            </tr>
                            <tr>
                                <td>1</td>
                                <td>Паренхіма аденогіпофізу представлена трабекулами...</td>
                                <td>Аденогіпофіз – частина гіпофізу (Hypophysis, glandula pituitaria) – центрального ендокринного...</td>
                                <td>Гістологія, Біологія|Загальна лікарська підготовка</td>
                                <td>Хромофобні клітини</td>
                                <td>Хромофорні становлять 60% клітинної маси дистальної частки гіпофіза...</td>
                                <td>-</td>
                            </tr>

                            <tr>
                                <td>2</td>
                                <td>Захворювання бері-бері - це класична форма недостатності вітаміну тіаміну...</td>
                                <td>Тіамін, Вітамін В1, антиневритний.У складі тіаміну наявне піримідинове кільце...</td>
                                <td>Біологічна хімія, Фармакологія</td>
                                <td>Трансфераз</td>
                                <td>Траснферази - ферменти, що каналізують перенесення хімічних груп з однієї молекули субстрату на іншу.</td>
                                <td>+</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Захворювання бері-бері - це класична форма недостатності вітаміну тіаміну...</td>
                                <td>Тіамін, Вітамін В1, антиневритний.У складі тіаміну наявне піримідинове кільце...</td>
                                <td>Біологічна хімія, Фармакологія</td>
                                <td>Оксидоредуктаз</td>
                                <td>Оксидоредуктази -ферменти, що каналізують окисно-відновні реакції. Утворення ТДФ...</td>
                                <td>-</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Захворювання бері-бері - це класична форма недостатності вітаміну тіаміну...</td>
                                <td>Тіамін, Вітамін В1, антиневритний.У складі тіаміну наявне піримідинове кільце...</td>
                                <td>Біологічна хімія, Фармакологія</td>
                                <td>Гідролаз</td>
                                <td>Гідролази – ферменти що каналізують гідроліз хімічних зв’язків. Під час реакції...</td>
                                <td>-</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Захворювання бері-бері - це класична форма недостатності вітаміну тіаміну...</td>
                                <td>Тіамін, Вітамін В1, антиневритний.У складі тіаміну наявне піримідинове кільце...</td>
                                <td>Біологічна хімія, Фармакологія</td>
                                <td>Ліаз</td>
                                <td>Ліази - ферменти, що каналізують розрив хімічних зв’язків без гідролізу, з утворенням...</td>
                                <td>-</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Захворювання бері-бері - це класична форма недостатності вітаміну тіаміну...</td>
                                <td>Тіамін, Вітамін В1, антиневритний.У складі тіаміну наявне піримідинове кільце...</td>
                                <td>Біологічна хімія, Фармакологія</td>
                                <td>Ізомераз</td>
                                <td>Ізомерази - ферменти, що каналізують структурні або геометричні зміни субстрату. Утврення ТДФ...</td>
                                <td>-</td>
                            </tr>
                        </tbody>
                    </Table>
                    <Alert type="warning">
                        <span className="weight-strong">УВАГА!</span> При створенні таблиці з тестами, потрібно пам'ятати про:
                        <ul className="browser-default">
                            <li>Всі колонки є опціональними. За винятком колонки <span className="weight-strong">"#"</span> у CSV файлі. Саме по ній групуються рядки та утворюється тест</li>
                            <li>Також файл може містити додаткові колонки</li>
                            <li>В першому рядку файлу обов'язково повинні міститись заголовки таблиці</li>
                            <li>Якщо тест містить декілька предметів, то вони повинні бути розділені <span className="weight-strong">комою</span></li>
                            <li>Якщо предмет може бути присутнім у декількох спеціалізаціях, то предмет записуємо у форматі: <span className="weight-strong">Предмет | Спеціалізація</span>. Інакше, Astra не зможе розпізнати потрібний предмет та не додасть його до тесту</li>
                        </ul>
                    </Alert>
                </div>
                <Spacer height={10} />
                <div className="header">Імпорт з веб-ресурсу</div>
                <div className="content">
                    На даний момент, Astra дозволяє імпортувати тести з наступних веб-ресурсів:
                    <ul className="browser-default">
                        <li><a href="https://тестування.укр/" target="_blank">тестування.укр</a></li>
                    </ul>
                    Для того, щоб запустити імпорт, необхідно зайти на сайт, обрати потрібну спеціалізацію, відкрити базу тестів
                    <img className="full-width" src={testingUkrTestbase} alt="testing.ukr" />
                    скопіювати посилання
                    <img className="full-width" src={testingUkrTestbaseLink} alt="testing.ukr" />
                    та вставити у відповідне поле:
                    <div className="full-width s-hflex-center">
                        <img src={webImport} alt="testing.ukr" />
                    </div>
                </div>
                <Spacer height={20} />
            </div>
            <InfoHeader>Експорт</InfoHeader>
            <div className="chapter">
                <div className="content">
                    Astra надає можливість експорту тестів у Excel або ж у CSV файл. Для цього необхідно заповнити наступну форму:
                    <div className="full-width s-hflex-center">
                        <img src={exportForm} alt="testing.ukr" />
                    </div>
                    Для того, щоб розпочати експорт, достатньо тільки вибрати тип файлу та натиснути на кнопку "Експортувати". Після цього Astra розпочне експорт всієї бази тестів до файлу.
                    Також, Ви можете обрати спеціалізацію для того, щоб Astra експортувала тести тільки для обраної спеціалізації.
                </div>
            </div>
            <Spacer height={20} />
        </div>
    );
}

export default withTitle(TransferDocumentationPage, "Документація - Імпорт/Експорт");
