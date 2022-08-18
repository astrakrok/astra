import {page} from "../../../../../constant/page";
import {getDetailedTests} from "../../../../../service/test.service";
import Button from "../../../../Button/Button";
import withTitle from "../../../../hoc/withTitle/withTitle";
import TestsList from "../../../../TestsList/TestsList";
import "./AllTestsPage.css";
import InfoText from "../../../../InfoText/InfoText";
import Paginated from "../../../../Paginated/Paginated";

const AllTestsPage = () => {
    const fetchPage = async (pageSize, pageNumber) => {
        return await getDetailedTests(pageSize, pageNumber);
    }

    return (
        <div className="AllTestsPage container">
            <div className="row">
                <div className="s-hflex-end">
                    <Button to={page.admin.tests.create} isFilled={true}>Створити</Button>
                </div>
                <div className="tests-list s-hflex-center">
                    <Paginated pageHandler={fetchPage}>
                        {
                            (items, orderFrom) => (
                                items.length > 0 ? (
                                    <TestsList tests={items} orderFrom={orderFrom}/>
                                ) : (
                                    <InfoText>
                                        Тести відсутні
                                    </InfoText>
                                )
                            )
                        }
                    </Paginated>
                </div>
            </div>
        </div>
    );
}

export default withTitle(AllTestsPage, "Тести");
