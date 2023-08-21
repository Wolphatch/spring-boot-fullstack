import { 
  Wrap, 
  WrapItem, 
  Spinner, 
  Text 
} from '@chakra-ui/react'
import SideBarWithHeader from './components/shared/SideBar'
import { useEffect, useState } from 'react'
import { getCustomers } from './services/client'
import SidebarWithHeader from './components/shared/SideBar'
import CardWithImage from './components/Card'


const App = ()=>{

  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(()=>{

    setLoading(true)
    setTimeout(() => {
      getCustomers().then(res=>{
        setCustomers(res.data)
        console.log(res)
      }).catch(err=>{
        console.log(err)
      }).finally(()=>{
        setLoading(false)
      })
    }, 0);
  },[])

  if (loading){
    return (
    <SidebarWithHeader>
      <Spinner
        thickness='4px'
        speed='0.65s'
        emptyColor='gray.200'
        color='blue.500'
        size='xl'
      />
    </SidebarWithHeader>
  )}
  
  if(customers.length<=0){
    return (
      <SideBarWithHeader>
      <Text>No Customer is available</Text>
    </SideBarWithHeader>
    )
  }

  return (
    <SideBarWithHeader>
      <Wrap justify={"center"} spacing={"30px"}>
        {customers.map((customer, index)=>{
          return (
            <WrapItem key={index}>
              <CardWithImage {...customer}/>
            </WrapItem>
          )
        })}
      </Wrap>
    </SideBarWithHeader>
  )
}

export default App;