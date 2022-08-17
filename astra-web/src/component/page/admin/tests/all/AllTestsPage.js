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
        const result = await getDetailedTests(pageSize, pageNumber);
        return {
            items: result,
            totalPages: 20
        }
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
                            items => (
                                items.length > 0 ? (
                                    <TestsList tests={items}/>
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
