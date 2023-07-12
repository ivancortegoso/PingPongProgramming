import React from 'react'
import '../styles/TransactionsStyle.css'
import {TransactionItem} from "./TransactionItem";
import {Auth} from "../../Auth";
import {useParams, Route, Routes} from "react-router-dom";
import {useState, useEffect} from 'react'
import { TransparentBlackBackground } from '../TransparentBlackBackground';
import { TransactionCreateWeb } from './TransactionCreateWeb';


export const TransactionsWeb = () => {
    const [transactionList, setTransactionList] = useState([]);
    const [page, setPage] = useState(0);
    const {filter} = useParams();

    const fetchTransactions = async () => {
        const response = await fetch("http://localhost:8080/api/transaction/" + filter, {
            headers: {
                'Authorization' : Auth.GetAuth()
            }
        });
        const transList = await response.json();
        setTransactionList(transList);
    }

    useEffect(() => {fetchTransactions()}, [filter]);

    const prevPage = () => {
        let old = page;
        if(old > 0)
            old = old - 1;
        setPage(old);
    }

    const nextPage = () => {
        let old = page;
        if(old < Math.floor(transactionList.length / 9))
            old = old + 1;
        setPage(old);
    }

    let sublist = transactionList.slice(page*9, page*9 + 9);

    return (
        <div className={"TransactionsWeb"}>
            <div className={"TransactionsWeb-List ShadowBox"}>
                <b>Transactions</b>
                { sublist.length > 0 &&
                    sublist.map((item) => {
                        return(
                            <React.Fragment key={item["id"]}>
                                <div className={"HSeparator"}/>
                                <TransactionItem item={item}/>
                            </React.Fragment>
                        )
                    })
                }
                { sublist.length === 0 && <div>Empty</div>}
                { transactionList.length > 9 &&
                    (<div>
                        <button onClick={prevPage}>{"<<"}</button>
                        <button onClick={nextPage}>{">>"}</button>
                    </div>)
                }

            </div>
            <Routes>
                <Route path="create" element={<><TransparentBlackBackground/><TransactionCreateWeb/></>}/>
            </Routes>
        </div>
    )
    
}

/*export class TransactionsWeb extends React.Component {

    constructor() {
        super();
        this.state = {
            transactionList: [],
        }
        this.fetchTransactions = this.fetchTransactions.bind(this)
    }

    async fetchTransactions() {
        const response = await fetch("http://localhost:8080/api/transaction/" + filter, {
            headers: {
                'Authorization' : Auth.GetAuth()
            }
        });
        const transList = await response.json();
        this.setState({transactionList: transList});
    }

    componentDidMount() {
        this.fetchTransactions()
    }

    render() {
        return (
            <div className={"TransactionsWeb"}>
                <div className={"TransactionsWeb-List ShadowBox"}>
                    Transactions
                    { this.state.transactionList.length > 0 &&
                        this.state.transactionList.map((item, index) => {
                            return(
                                <>
                                    {index == 0 ? "" : <div className={"HSeparator"}></div>}
                                    <TransactionItem item={item}/>
                                </>
                            )
                        })
                    }

                </div>
            </div>
        )
    }
}*/