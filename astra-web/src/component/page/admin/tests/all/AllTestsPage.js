import {page} from "../../../../../constant/page";
import {getDetailedTests} from "../../../../../service/test.service";
import Button from "../../../../Button/Button";
import withTitle from "../../../../hoc/withTitle/withTitle";
import TestsList from "../../../../TestsList/TestsList";
import "./AllTestsPage.css";
import InfoText from "../../../../InfoText/InfoText";
import Paginated from "../../../../Paginated/Paginated";
import Spacer from "../../../../Spacer/Spacer";
import {app} from "../../../../../constant/app";
import TestsFilter from "../../../../filter/TestsFilter/TestsFilter";
import {useSearchParams} from "react-router-dom";

const AllTestsPage = () => {
    const [searchParams] = useSearchParams();

    const fetchPage = async (filter, pageable) => {
        return await getDetailedTests(filter, pageable);
    }

    return (
        <div className="AllTestsPage container">
            <div className="row">
                <div className="s-hflex-end">
                    <Button to={page.admin.tests.create} isFilled={true}>Створити</Button>
                </div>
                <Spacer height={20} />
                <div className="tests-list s-hflex-center">
                    <Paginated pageSize={app.pageSize} pageHandler={fetchPage} initialFilter={{importId: searchParams.get("importId")}}>
                        {({
                            filter: ({initialFilter, setFilter}) => (
                                <TestsFilter initialFilter={initialFilter} onFilterSelected={setFilter} />
                            ),
                            content: (items, orderFrom) => (
                                items.length > 0 ? (
                                    <TestsList tests={items} orderFrom={orderFrom}/>
                                ) : (
                                    <InfoText>
                                        Тести відсутні
                                    </InfoText>
                                )
                            )
                        })}
                    </Paginated>
                </div>
            </div>
        </div>
    );
}

export default withTitle(AllTestsPage, "Тести");
