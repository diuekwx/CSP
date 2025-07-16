import CalendarHeatmap from 'react-calendar-heatmap';
import { useEffect, useState } from 'react';

interface Dates{
    date: string,
    count: number
}

export default function HomePage() {
    const today = new Date();
    const pad = (num: number) => String(num).padStart(2, '0');

    const [dates, setDates] = useState<Dates[]>([]);
    const[year, setYears] = useState<number>(today.getFullYear());
    
    const [startDate, setStartDate] = useState<string>(`${today.getFullYear()}-01-01`);
    const [endDate, setEndDate] = useState<string>
        (`${today.getFullYear()}-${pad(today.getMonth() + 1)}-${pad(today.getDate())}`);

    useEffect(() => {
    const fetchData =  async () => {
        const response = await fetch('localhost:5050/api/contribution/user-contributions');
        if (!response.ok){
            throw new Error(`httperror ${response.status}`);
        }
        const data = await response.json();
        setDates(data);
    }

    fetchData();
    
    }, []);

    return (
        <>
        <div> 
            <CalendarHeatmap
            startDate={new Date(startDate.toString())}
            endDate={new Date(endDate.toString())}
            values={dates}
            />
        </div>

        </>
    )
}