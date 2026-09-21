package com.digitalhealth.dto;
import java.util.*;
public class DashboardDTO { public Map<String,Long> counts=new LinkedHashMap<>(); public Object worker; public Object profile; public List<?> recentRecords=List.of(); public List<?> appointments=List.of(); public List<?> notifications=List.of(); }
