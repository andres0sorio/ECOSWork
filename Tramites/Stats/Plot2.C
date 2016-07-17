void Plot2()
{
//=========Macro generated from canvas: Plot2/Canvas for plot 1
//=========  (Sun Jul 17 11:26:14 2016) by ROOT version6.06/04
   TCanvas *Plot2 = new TCanvas("Plot2", "Canvas for plot 1",257,243,607,454);
   Plot2->SetHighLightColor(2);
   Plot2->Range(-6.250001,-4.331251,56.25,38.98125);
   Plot2->SetFillColor(10);
   Plot2->SetBorderMode(0);
   Plot2->SetBorderSize(2);
   Plot2->SetFrameFillColor(0);
   Plot2->SetFrameBorderMode(0);
   Plot2->SetFrameBorderMode(0);
   
   TH1F *Exp1__1 = new TH1F("Exp1__1","Population 2017 (DANE)",10,0,50);
   Exp1__1->SetBinContent(1,22);
   Exp1__1->SetBinContent(2,33);
   Exp1__1->SetBinContent(3,23);
   Exp1__1->SetBinContent(4,11);
   Exp1__1->SetBinContent(5,5);
   Exp1__1->SetBinContent(6,7);
   Exp1__1->SetBinContent(7,2);
   Exp1__1->SetBinContent(8,3);
   Exp1__1->SetEntries(106);
   
   TPaveStats *ptstats = new TPaveStats(0.78,0.775,0.98,0.935,"brNDC");
   ptstats->SetName("stats");
   ptstats->SetBorderSize(1);
   ptstats->SetFillColor(0);
   ptstats->SetTextAlign(12);
   ptstats->SetTextFont(42);
   TText *AText = ptstats->AddText("Exp1");
   AText->SetTextSize(0.04906667);
   AText = ptstats->AddText("Entries = 106    ");
   AText = ptstats->AddText("Mean  =  12.09");
   ptstats->SetOptStat(111);
   ptstats->SetOptFit(0);
   ptstats->Draw();
   Exp1__1->GetListOfFunctions()->Add(ptstats);
   ptstats->SetParent(Exp1__1);
   Exp1__1->SetFillColor(5);

   Int_t ci;      // for color index setting
   TColor *color; // for color definition with alpha
   ci = TColor::GetColor("#000099");
   Exp1__1->SetLineColor(ci);
   Exp1__1->GetXaxis()->SetTitle("Poblacion [k]");
   Exp1__1->GetXaxis()->CenterTitle(true);
   Exp1__1->GetXaxis()->SetLabelFont(42);
   Exp1__1->GetXaxis()->SetLabelSize(0.035);
   Exp1__1->GetXaxis()->SetTitleSize(0.05);
   Exp1__1->GetXaxis()->SetTitleOffset(0.88);
   Exp1__1->GetXaxis()->SetTitleFont(42);
   Exp1__1->GetYaxis()->SetTitle("Sample");
   Exp1__1->GetYaxis()->CenterTitle(true);
   Exp1__1->GetYaxis()->SetLabelFont(42);
   Exp1__1->GetYaxis()->SetLabelSize(0.035);
   Exp1__1->GetYaxis()->SetTitleSize(0.05);
   Exp1__1->GetYaxis()->SetTitleOffset(0.98);
   Exp1__1->GetYaxis()->SetTitleFont(42);
   Exp1__1->GetZaxis()->SetLabelFont(42);
   Exp1__1->GetZaxis()->SetLabelSize(0.035);
   Exp1__1->GetZaxis()->SetTitleSize(0.035);
   Exp1__1->GetZaxis()->SetTitleFont(42);
   Exp1__1->Draw("");
   
   TPaveText *pt = new TPaveText(0.282562,0.9330282,0.717438,0.995,"blNDC");
   pt->SetName("title");
   pt->SetBorderSize(0);
   pt->SetFillColor(0);
   pt->SetFillStyle(0);
   pt->SetTextFont(132);
   AText = pt->AddText("Population 2017 (DANE)");
   pt->Draw();
   Plot2->Modified();
   Plot2->cd();
   Plot2->SetSelected(Plot2);
}
