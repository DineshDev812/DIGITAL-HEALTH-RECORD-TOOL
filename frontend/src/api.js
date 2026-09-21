// Configure VITE_API_URL in Vercel, for example https://api.example.com/api.
// The localhost value keeps the existing local development workflow working.
const BASE = (import.meta.env.VITE_API_URL || 'http://localhost:8081/api').replace(/\/$/, '');
export const auth=()=>localStorage.getItem('health_token');
export const logout=()=>{localStorage.removeItem('health_token');localStorage.removeItem('health_user');};
export async function api(path,options={}){const headers={...(options.body instanceof FormData?{}:{'Content-Type':'application/json'}),...(auth()?{Authorization:`Bearer ${auth()}`} : {}),...(options.headers||{})};const r=await fetch(`${BASE}${path}`,{...options,headers});if(!r.ok){const b=await r.json().catch(()=>({}));throw new Error(b.message||'Request could not be completed.');}if(r.status===204)return null;return r.headers.get('content-type')?.includes('application/json')?r.json():r;}
export async function download(path){const r=await fetch(`${BASE}${path}`,{headers:{Authorization:`Bearer ${auth()}`}});if(!r.ok)throw new Error('Download failed.');return r.blob();}
