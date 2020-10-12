/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package nivalsoul.kettle.plugins.util.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.RKRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.StringRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.google.common.collect.Lists;

/**
 * A XLS -> CSV processor, that uses the MissingRecordAware
 *  EventModel code to ensure it outputs all columns and rows.
 * @author Nick Burch
 */
public class XLS2CSVmra implements HSSFListener {
	private int minColumns;
	private POIFSFileSystem fs;
	private PrintStream output;

	private int lastRowNumber;
	private int lastColumnNumber;

	/** Should we output the formula, or the value it has? */
	private boolean outputFormulaValues = true;

	/** For parsing Formulas */
	private SheetRecordCollectingListener workbookBuildingListener;
	private HSSFWorkbook stubWorkbook;

	// Records we pick up as we process
	private SSTRecord sstRecord;
	private FormatTrackingHSSFListener formatListener;
	
	/** So we known which sheet we're on */
	private int sheetIndex = -1;
	private BoundSheetRecord[] orderedBSRs;
	private List<BoundSheetRecord> boundSheetRecords = new ArrayList<>();

	// For handling formulas with string results
	private int nextRow;
	private int nextColumn;
	private boolean outputNextStringRecord;
	
	private List<List<String>> dataList = Lists.newArrayList();
    private List<String> rowlist = new ArrayList<String>();

	/**
	 * Creates a new XLS -> CSV converter
	 * @param fs The POIFSFileSystem to process
	 * @param output The PrintStream to output the CSV to
	 * @param minColumns The minimum number of columns to output, or -1 for no minimum
	 */
	public XLS2CSVmra(POIFSFileSystem fs, PrintStream output, int minColumns) {
		this.fs = fs;
		this.output = output;
		this.minColumns = minColumns;
	}

	/**
	 * Creates a new XLS -> CSV converter
	 * @param filename The file to process
	 * @param minColumns The minimum number of columns to output, or -1 for no minimum
	 */
	public XLS2CSVmra(String filename, int minColumns) throws IOException, FileNotFoundException {
		this(
				new POIFSFileSystem(new FileInputStream(filename)),
				System.out, minColumns
		);
	}

	/**
	 * Initiates the processing of the XLS file to CSV
	 * @return 
	 */
	public List<List<String>> process() throws IOException {
		MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
		formatListener = new FormatTrackingHSSFListener(listener);

		HSSFEventFactory factory = new HSSFEventFactory();
		HSSFRequest request = new HSSFRequest();

		if(outputFormulaValues) {
			request.addListenerForAllRecords(formatListener);
		} else {
			workbookBuildingListener = new SheetRecordCollectingListener(formatListener);
			request.addListenerForAllRecords(workbookBuildingListener);
		}

		factory.processWorkbookEvents(request, fs);
		
		return dataList;
	}

	/**
	 * Main HSSFListener method, processes events, and outputs the
	 *  CSV as the file is processed.
	 */
	@Override
    public void processRecord(Record record) {
		int thisRow = -1;
		int thisColumn = -1;
		String thisStr = null;

		switch (record.getSid())
		{
		case BoundSheetRecord.sid:
			boundSheetRecords.add((BoundSheetRecord)record);
			break;
		case BOFRecord.sid:
			BOFRecord br = (BOFRecord)record;
			if(br.getType() == BOFRecord.TYPE_WORKSHEET) {
				// Create sub workbook if required
				if(workbookBuildingListener != null && stubWorkbook == null) {
					stubWorkbook = workbookBuildingListener.getStubHSSFWorkbook();
				}
				
				// Output the worksheet name
				// Works by ordering the BSRs by the location of
				//  their BOFRecords, and then knowing that we
				//  process BOFRecords in byte offset order
				sheetIndex++;
				if(orderedBSRs == null) {
					orderedBSRs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);
				}
				//output.println();
				//output.println(orderedBSRs[sheetIndex].getSheetname() +" [" + (sheetIndex+1) + "]:");
			}
			break;

		case SSTRecord.sid:
			sstRecord = (SSTRecord) record;
			break;

		case BlankRecord.sid:
			BlankRecord brec = (BlankRecord) record;

			thisRow = brec.getRow();
			thisColumn = brec.getColumn();
			thisStr = "";
			rowlist.add(thisStr);
			break;
		case BoolErrRecord.sid:
			BoolErrRecord berec = (BoolErrRecord) record;

			thisRow = berec.getRow();
			thisColumn = berec.getColumn();
			thisStr = "";
			rowlist.add(thisStr);
			break;

		case FormulaRecord.sid:
			FormulaRecord frec = (FormulaRecord) record;

			thisRow = frec.getRow();
			thisColumn = frec.getColumn();

			if(outputFormulaValues) {
				if(Double.isNaN( frec.getValue() )) {
					// Formula result is a string
					// This is stored in the next record
					outputNextStringRecord = true;
					nextRow = frec.getRow();
					nextColumn = frec.getColumn();
				} else {
					thisStr = formatListener.formatNumberDateCell(frec);
					rowlist.add(thisStr);
				}
			} else {
				String value = HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedExpression());
				thisStr = '"' + value + '"';
				rowlist.add(value);
			}
			break;
		case StringRecord.sid:
			if(outputNextStringRecord) {
				// String for formula
				StringRecord srec = (StringRecord)record;
				thisStr = srec.getString();
				rowlist.add(thisStr);
				thisRow = nextRow;
				thisColumn = nextColumn;
				outputNextStringRecord = false;
			}
			break;

		case LabelRecord.sid:
			LabelRecord lrec = (LabelRecord) record;

			thisRow = lrec.getRow();
			thisColumn = lrec.getColumn();
			thisStr = '"' + lrec.getValue() + '"';
			rowlist.add(lrec.getValue());
			break;
		case LabelSSTRecord.sid:
			LabelSSTRecord lsrec = (LabelSSTRecord) record;

			thisRow = lsrec.getRow();
			thisColumn = lsrec.getColumn();
			if(sstRecord == null) {
				thisStr = '"' + "(No SST Record, can't identify string)" + '"';
				rowlist.add("(No SST Record, can't identify string)");
			} else {
				thisStr = '"' + sstRecord.getString(lsrec.getSSTIndex()).toString() + '"';
				rowlist.add(sstRecord.getString(lsrec.getSSTIndex()).toString());
			}
			break;
		case NoteRecord.sid:
			NoteRecord nrec = (NoteRecord) record;

			thisRow = nrec.getRow();
			thisColumn = nrec.getColumn();
			// TODO: Find object to match nrec.getShapeId()
			thisStr = '"' + "(TODO)" + '"';
			rowlist.add("(TODO)");
			break;
		case NumberRecord.sid:
			NumberRecord numrec = (NumberRecord) record;

			thisRow = numrec.getRow();
			thisColumn = numrec.getColumn();

			// Format
			thisStr = formatListener.formatNumberDateCell(numrec);
			rowlist.add(thisStr);
			break;
		case RKRecord.sid:
			RKRecord rkrec = (RKRecord) record;

			thisRow = rkrec.getRow();
			thisColumn = rkrec.getColumn();
			thisStr = '"' + "(TODO)" + '"';
			rowlist.add("(TODO)");
			break;
		default:
			break;
		}

		// Handle new row
		if(thisRow != -1 && thisRow != lastRowNumber) {
			lastColumnNumber = -1;
		}

		// Handle missing column
		if(record instanceof MissingCellDummyRecord) {
			MissingCellDummyRecord mc = (MissingCellDummyRecord)record;
			thisRow = mc.getRow();
			thisColumn = mc.getColumn();
			thisStr = "";
			rowlist.add(thisStr);
		}

		// If we got something to print out, do so
		if(thisStr != null) {
			/*if(thisColumn > 0) {
				output.print(',');
			}
			output.print(thisStr);*/
		}

		// Update column and row count
		if(thisRow > -1)
			lastRowNumber = thisRow;
		if(thisColumn > -1)
			lastColumnNumber = thisColumn;

		// Handle end of row
		if(record instanceof LastCellOfRowDummyRecord) {
			// Print out any missing commas if needed
			if(minColumns > 0) {
				// Columns are 0 based
				if(lastColumnNumber == -1) { lastColumnNumber = 0; }
				for(int i=lastColumnNumber; i<(minColumns); i++) {
					output.print(',');
					rowlist.add(null);
				}
			}

			// We're onto a new row
			lastColumnNumber = -1;

			// End the row
			//output.println();
			dataList.add(rowlist);
			rowlist = Lists.newArrayList();
		}
	}
    
	public List<List<String>> getData(){
		return dataList;
	}
	
	public static List<List<String>> readToList(InputStream is) throws Exception {
		XLS2CSVmra xls2csv = new XLS2CSVmra(new POIFSFileSystem(is), System.out, -1);
		List<List<String>> list = xls2csv.process();

		return list;
	} 

	public static void main(String[] args) throws Exception {

		String filename = "F:\\FTP\\abc\\aaa.xls";
		int minColumns = -1;

		XLS2CSVmra xls2csv = new XLS2CSVmra(filename, minColumns);
		List<List<String>> list = xls2csv.process();
		System.out.println(list);
	}
}
